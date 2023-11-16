package ingenium.scenarios;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.jagacy.Login;
import com.jagacy.util.JagacyException;
import com.keyword.Driver;
import com.keyword.Keyword;
import com.model.EntryField;
import com.model.LabelField;
import com.model.UWDETable;
import com.utils.CommonMapping;
import com.utils.Data;
import static com.utils.CommonMapping.*;
import static com.keyword.Keyword.*;
import com.controller.*;

public class TaxacaoEmBloco {
	private Data data;
	private Keyword kw;
	public static final int TABLE_LENGTH = 5;
	public static final int PAGINATE_LIMIT = 10;

	public TaxacaoEmBloco(final Driver session, Data data) throws JagacyException {
		this.data = data;
		this.kw = new Keyword(session);
	}

	public String changeInitials(String initials1) throws JagacyException, InterruptedException {
		inputAndEnter(UNDERWRITER_DECISION_LIFE_CLIENT_FIELD_COMMAND, "uwwk");
		write(UWWK_INITIALS);
		delete(UWWK_INITIALS);
		input(UWWK_INITIALS, initials1);
		input(UWWK_CLIENT_NUMBER, data.getClient());
		inputAndEnter(UWWK_ENTER, "M");
		input(UWWK_NEW_INITIALS, data.getUsernameIngenium());
		inputAndEnter(UWWK_FUNCTION, "RR");
		if (!waitForTextLabel(MESSAGE_MAINTENANCE_OK))
			return "ERRO - MENSAGEM DE CONFIRMAÇÃO DE MANUTENÇÃO NÃO FOI EXIBIDA";
		inputAndEnter(UNDERWRITER_DECISION_LIFE_CLIENT_FIELD_COMMAND, "uwde");
		return "";
	}

	public boolean isSameInitials(String initials1) {
		return initials1.equalsIgnoreCase(data.getUsernameIngenium());
	}

	public String validateInitialsChanging() throws JagacyException, InterruptedException {
		inputAndEnter(UNDERWRITER_DECISION_LIFE_CLIENT, data.getClient());
		String initials_validate = read(UNDERWRITER_DECISION_LIFE_INITIALS, 8);
		if (!data.getUsernameIngenium().equalsIgnoreCase(initials_validate))
			return "ERRO - A ALTERAÇÃO DE INITIALS FALHOU";
		return "";
	}

	public String run()
			throws JagacyException, InterruptedException, NoSuchAlgorithmException, UnsupportedEncodingException {
		// 0000933427
//massa uma vez gasta, precisa de uma nova linha no script para refazer.
		try {
			inputAndEnter(FIELD_COMMAND, "uwde");
			input(UNDERWRITER_DECISION_LIFE_CLIENT, data.getClient());
			inputAndEnter(UWDE_ENTER, "M");
			waitForChange(getDriver().DEFAULT_TIMEOUT);
			String initials1 = read(UNDERWRITER_DECISION_LIFE_INITIALS, 8).trim();
			if (!isSameInitials(initials1)) {
				changeInitials(initials1);
				String msg = validateInitialsChanging();
				if (!msg.isEmpty())
					return msg;
				inputAndEnter(UWDE_ENTER, "M");
				waitForChange(getDriver().DEFAULT_TIMEOUT);
			}

			input(UWDE_STB2, data.getUwde_stb2());
			inputAndEnter(UWDE_R, data.getUwde_r());

			String pageValidation;

			List<UWDETable> regs = collectAllTablesByPage();

			// valida se alguma dos registros contém inconsistências
			inputAndEnter(FIELD_COMMAND_AUX, "plan");
			pageValidation = validateAllTable(regs);
			if (!pageValidation.isEmpty()) {
				return pageValidation;
			}

			return null;
		} catch (Exception e) {
			if(isKeyboarLocked())
				return "Aplicação se encontra em locked";
			return read(OTHER_MESSAGE) +"\n"+read(OTHER_MESSAGE2) +"\n"+read(OTHER_MESSAGE3) +"\n";
		}
	}

	public void paginateDown() throws InterruptedException, JagacyException {
		int count = 1;
		while (!endOfList()
				&& !read(UWDE_MORE_DETAILS.getRow(), UWDE_MORE_DETAILS.getColumn(), UWDE_MORE_DETAILS.getLength())
						.trim().contains("TO VIEW MORE DATA PRESS ENTER")) {
			enter();
			count++;
			if (!waitForChange(getDriver().DEFAULT_TIMEOUT) && count > 2)
				return;
//			if (count >= PAGINATE_LIMIT)
//				throw new InterruptedException("Erro na paginação ao realizar page down.");
		}
		inputAndEnter(UWDE_PAGE, "D");
		waitForChange(getDriver().DEFAULT_TIMEOUT);
	}

	public void paginateUp() throws InterruptedException, JagacyException {
		int count = 1;
		while (!endOfList()
				&& !read(UWDE_MORE_DETAILS.getRow(), UWDE_MORE_DETAILS.getColumn(), UWDE_MORE_DETAILS.getLength())
						.trim().contains("TO VIEW MORE DATA PRESS ENTER")) {
			enter();
			count++;
			if (!waitForChange(getDriver().DEFAULT_TIMEOUT) && count > 2)
				return;
//			if (count >= PAGINATE_LIMIT)
//				throw new InterruptedException("Erro na paginação ao realizar page down.");
		}
		inputAndEnter(UWDE_PAGE, "U");
		waitForChange(getDriver().DEFAULT_TIMEOUT);
	}

//	public void validateByPage() throws JagacyException, InterruptedException {
//		while (!endOfList()) {
//			paginateDown();
//		}
//	}

	public boolean endOfList() throws JagacyException {
		return waitForTextLabel(MESSAGE_END_OF_LIST);
	}

	public List<UWDETable> collectAllTablesByPage() throws JagacyException, InterruptedException {
		// coleta todos as linhas da tabela, página por página

		List<UWDETable> regs = new ArrayList<>();
		regs.addAll(UWDETableController.readAllUWDE());
		if (regs.size() == TABLE_LENGTH) {
			regs.clear();
			do {
				regs.addAll(UWDETableController.readAllUWDE());
				paginateDown();
				UWDETable reg = UWDETableController.readUWDE(1);
				// se for só uma página ele já encerra neste ponto
				if (tableContainsReg(regs, reg)) {
					break;
				} else {
					// caso tenha atingido o fim da lista após a paginação, adiciona tudo e encerra
					if (endOfList()) {
						regs.addAll(UWDETableController.readAllUWDE());
						break;
					}
				}
			} while (!endOfList());

		}
		return regs;
	}

	private static boolean tableContainsReg(List<UWDETable> regs, UWDETable reg) {
		// o método contains da list não funcionou e foi necessario cria-lo
		for (Iterator iterator = regs.iterator(); iterator.hasNext();) {
			UWDETable uwdeTable = (UWDETable) iterator.next();
			if (uwdeTable.getCardinal().equalsIgnoreCase(reg.getCardinal())
					&& uwdeTable.getPolicy().equalsIgnoreCase(reg.getPolicy())
					&& uwdeTable.getPlan().equalsIgnoreCase(reg.getPlan()))
				return true;

		}
		return false;
	}

	public String validateAllTable(List<UWDETable> regs) throws JagacyException, InterruptedException {
		String validate = "";
		for (Iterator iterator = regs.iterator(); iterator.hasNext();) {
			UWDETable uwdeTable = (UWDETable) iterator.next();
			if (Rules.isBasicPlan(uwdeTable)) {
				List<UWDETable> cleanRegs = Rules.getCoberturaAdicional(regs, uwdeTable);
				validate = UWDETableController.validateRules(cleanRegs, uwdeTable, data);
			}
			if (!validate.isEmpty()) {
				break;
			}
		}
		return validate;
	}
}
