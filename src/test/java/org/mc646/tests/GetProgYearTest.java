package org.mc646.tests;

import org.isf.OHCoreTestCase;
import org.isf.disease.model.Disease;
import org.isf.disease.service.DiseaseIoOperationRepository;
import org.isf.distype.service.DiseaseTypeIoOperationRepository;
import org.isf.opd.manager.OpdBrowserManager;
import org.isf.opd.service.OpdIoOperationRepository;
import org.isf.patient.model.Patient;
import org.isf.patient.service.PatientIoOperationRepository;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runners.Parameterized;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;

import static org.junit.Assert.fail;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class GetProgYearTest extends OHCoreTestCase {

	@Autowired
	OpdBrowserManager opdManager = new OpdBrowserManager();
	@Autowired
	OpdIoOperationRepository opdIoOperationRepository;
	@Autowired
	PatientIoOperationRepository patientIoOperationRepository;
	@Autowired
	DiseaseTypeIoOperationRepository diseaseTypeIoOperationRepository;
	@Autowired
	DiseaseIoOperationRepository diseaseIoOperationRepository;

	static DefaultDataSetUp defaultData;
	static DataSetUp dataSetUp = new DataSetUp();


	@Parameterized.Parameter(0)
	public boolean databaseConnection;

	@Parameterized.Parameter(1)
	public int year;

	@Parameterized.Parameter(2)
	public int expectedProgYear;

	@Parameterized.Parameter(3)
	public String expectedException;


	@Parameterized.Parameters
	public static Iterable<Object[]> data() {
		return Arrays.asList(new Object[][] {
			/* 1 */ { true, 0, 1, null },
			/* 2 */ { true, 1, 0, null },
			/* 3 */ { false, 1, null, "java.lang.NullPointerException" },
			/* 4 */ { true, null, null, "org.isf.utils.exception.OHServiceException" },
			/* 5 */ { true, -1, null, "org.isf.utils.exception.OHServiceException" } });
	}


	@BeforeClass
	public static void setUpClass() throws Exception {
		defaultData = new DefaultDataSetUp();
	}

	@Before
	public void setUp() throws Exception {
		cleanH2InMemoryDb();
		DefaultDataSetUp defaultData = new DefaultDataSetUp();

		for (int i = 0; i < defaultData.getDefaultDiseaseTypes().size(); i++) {
			diseaseTypeIoOperationRepository.saveAndFlush(defaultData.getDefaultDiseaseTypes().get(i));
		}

		for (int i = 0; i < defaultData.getDefaultDiseases().size(); i++) {
			Disease d = diseaseIoOperationRepository.saveAndFlush(defaultData.getDefaultDiseases().get(i));
		}

		for (int i = 0; i < defaultData.getDefaultPatients().size(); i++) {
			Patient p = patientIoOperationRepository.saveAndFlush(defaultData.getDefaultPatients().get(i));
		}

		for (int i = 0; i < defaultData.getDefaultOpds().size(); i++) {
			opdIoOperationRepository.saveAndFlush(defaultData.getDefaultOpds().get(i));
		}
	}

	@After
	public void tearDown() throws Exception {
		cleanH2InMemoryDb();
	}

	@Test
	public void testCase() throws Exception {
		if (expectedException != null) {
			try {
				if (!databaseConnection) {
					OpdBrowserManager disabled = new OpdBrowserManager();
					int progYear = disabled.getProgYear(year);
				} else {
					int progYear = opdManager.getProgYear(year);
				}
				fail("Expecting exception: " + expectedException);
			} catch (Exception e) {
				assertEquals(expectedException, e.getClass().getName());
			}
		} else {
			int progYear = opdManager.getProgYear(year);
			assertEquals(expectedProgYear, progYear);
		}
	}

}
