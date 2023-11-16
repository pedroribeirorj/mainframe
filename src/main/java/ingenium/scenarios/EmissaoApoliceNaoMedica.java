package ingenium.scenarios;

import com.utils.Data;
import com.jagacy.util.JagacyException;
import com.keyword.Driver;
import com.model.EntryField;
import com.model.LabelField;

public class EmissaoApoliceNaoMedica {
	private Driver driver;
	private Data data;

	

	public EmissaoApoliceNaoMedica(final Driver session, Data data) throws JagacyException {
		this.data = data;
		this.driver = session;

	}

	public String run() throws JagacyException, InterruptedException {

		return "ERROR - EMISSÃO DE APOLICES NAO MEDICAS UW AUTO";
	}
}
