package ingenium.scenarios;

import com.utils.Data;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

import com.jagacy.Login;
import com.jagacy.util.JagacyException;
import com.keyword.Driver;
import com.keyword.Keyword;
import com.model.EntryField;
import com.model.LabelField;
import static com.utils.CommonMapping.*;
import static com.keyword.Keyword.*;

public class EmissaoApolicesNaoMedicasUWAuto {
	private Keyword kw;
	private EntryField FIELD_PPRO = new EntryField(18, 32);

	private Data data;

	public EmissaoApolicesNaoMedicasUWAuto(final Driver session, Data data) throws JagacyException {
		this.data = data;
		this.kw = new Keyword(session);
	}

	public String run()
			throws JagacyException, InterruptedException, NoSuchAlgorithmException, UnsupportedEncodingException {
		try {
			inputAndEnter(FIELD_COMMAND, "APEN");

			input(FIELD_POLICY, data.getPolicy());
			inputAndEnter(FIELD_ENTER, "M");
			input(FIELD_SUPRESS_ISS_SET, "N");
			inputAndEnter(FIELD_COMMAND_APPLICATION_ENTRY, "PPRO");
			input(FIELD_OWNER_NAME, data.getFirstSurnameLetters());
			input(FIELD_POLICY_PROCESSING_ENTER, "U");
			enter();
			enter();
			input(FIELD_POLICY_PROCESSING_ENTER, "S");
			enter();
			if (waitForTextLabel(MESSAGE_OK_CC59500001) && waitForTextLabel(MESSAGE_OK_CC60600001))
				return null;
			return read(OTHER_MESSAGE);
		} catch (Exception e) {
			return e.getMessage();
		}
	}
}