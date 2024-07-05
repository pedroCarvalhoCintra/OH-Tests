package org.mc646.tests.inferface;

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
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.rules.SpringClassRule;
import org.springframework.test.context.junit4.rules.SpringMethodRule;

import java.util.Arrays;

import static org.junit.Assert.fail;
import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(Parameterized.class)
public class IsExistOpdNumTest extends OHCoreTestCase {
	@ClassRule
	public static final SpringClassRule springClassRule = new SpringClassRule();

	@Rule
	public final SpringMethodRule springMethodRule = new SpringMethodRule();
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
	public Integer opdNum;

	@Parameterized.Parameter(2)
	public Integer year;

	@Parameterized.Parameter(3)
	public boolean expectedResult;

	@Parameterized.Parameter(4)
	public String expectedException;


	@Parameterized.Parameters
	public static Iterable<Object[]> data() {
		return Arrays.asList(new Object[][] {
			/* 1 */ { true, 1, 1, false, null },
			/* 2 */ { false, 1, 1, false, "java.lang.NullPointerException" },
			/* 3 */ { true, null, 1, false, "org.isf.utils.exception.OHServiceException" },
			/* 4 */ { true, 0, 1, false, "org.isf.utils.exception.OHServiceException" },
			/* 5 */ { true, 10, 1, false, null },
			/* 6 */ { true, 1, null, false, "org.isf.utils.exception.OHServiceException" },
			/* 7 */ { true, 1, 0, true, null },
			/* 8 */ { true, 1, 1900, false, null } });
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
					boolean result = disabled.isExistOpdNum(opdNum, year);
				} else {
					boolean result = opdManager.isExistOpdNum(opdNum, year);
				}
				fail("Expecting exception: " + expectedException);
			} catch (Exception e) {
				assertEquals(expectedException, e.getClass().getName());
			}
		} else {
			boolean result = opdManager.isExistOpdNum(opdNum, year);
			assertEquals(expectedResult, result);
		}
	}

}
