package org.mc646.tests.inferface;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.GregorianCalendar;
import java.util.List;

import org.isf.OHCoreTestCase;
import org.isf.disease.model.Disease;
import org.isf.disease.service.DiseaseIoOperationRepository;
import org.isf.distype.service.DiseaseTypeIoOperationRepository;
import org.isf.opd.manager.OpdBrowserManager;
import org.isf.opd.model.Opd;
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
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.rules.SpringClassRule;
import org.springframework.test.context.junit4.rules.SpringMethodRule;

@RunWith(Parameterized.class)
public class GetOpdMultipleParamsTest extends OHCoreTestCase {
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

	@Parameter(0)
	public boolean databaseConnection;

	@Parameter(1)
	public String disTypeCode;

	@Parameter(2)
	public String disCode;

	@Parameter(3)
	public GregorianCalendar dateFrom;

	@Parameter(4)
	public GregorianCalendar dateTo;

	@Parameter(5)
	public Integer ageFrom;

	@Parameter(6)
	public Integer ageTo;

	@Parameter(7)
	public Character sex;

	@Parameter(8)
	public Character newPatient;

	@Parameter(9)
	public Integer expectedSize;

	@Parameter(10)
	public List<String> expectedNames;

	@Parameter(11)
	public List<Integer> expectedCodes;

	@Parameter(12)
	public String expectedException;

	@Parameters
	public static Iterable<Object[]> data() {
		return Arrays.asList(new Object[][] {
				/* 0 */ { true, "L00-L99 Skin and subcutaneous tissue", "Acne", new GregorianCalendar(2003, 2, 2),
						new GregorianCalendar(2003, 2, 2), 0, 0, 'M', 'n', 1, new ArrayList<>(Arrays.asList("Bob")),
						new ArrayList<>(Arrays.asList(1)), null },
				/* 1 */ { true, "J00-J99 Respiratory system",
						"Influenza with pneumonia, seazonal influenza virus detected",
						new GregorianCalendar(2010, 3, 9), new GregorianCalendar(2010, 3, 9), 31, 31, 'F', 'R', 1,
						new ArrayList<>(Arrays.asList("Alice")), new ArrayList<>(Arrays.asList(2)), null },
				/* 2 */ { false, "L00-L99 Skin and subcutaneous tissue", "Acne", new GregorianCalendar(2003, 2, 2),
						new GregorianCalendar(2003, 2, 2), 0, 0, 'M', 'n', 0, null, null, "java.lang.NullPointerException" },
				/* 3 */ { true, null, "Acne", new GregorianCalendar(2003, 2, 2), new GregorianCalendar(2003, 2, 2), 0,
						0, 'M', 'n', 0, null, null, "org.isf.utils.exception.OHServiceException" },
				/* 4 */ { true, "W00-W99 Odd diseases", "Acne", new GregorianCalendar(2003, 2, 2),
						new GregorianCalendar(2003, 2, 2), 0, 0, 'M', 'n', 0, new ArrayList<>(), new ArrayList<>(),
						null },
				/* 5 */ { true, "L00-L99 Skin and subcutaneous tissue", null, new GregorianCalendar(2003, 2, 2),
						new GregorianCalendar(2003, 2, 2), 0, 0, 'M', 'n', 0, null, null, "org.isf.utils.exception.OHServiceException" },
				/* 6 */ { true, "L00-L99 Skin and subcutaneous tissue", "Urticaria", new GregorianCalendar(2003, 2, 2),
						new GregorianCalendar(2003, 2, 2), 0, 0, 'M', 'n', 0, new ArrayList<>(), new ArrayList<>(),
						null },
				/* 7 */ { true, "L00-L99 Skin and subcutaneous tissue", "Acne", null, new GregorianCalendar(2003, 2, 2),
						0, 0, 'M', 'n', 0, null, null, "org.isf.utils.exception.OHServiceException" },
				/* 8 */ { true, "L00-L99 Skin and subcutaneous tissue", "Acne", new GregorianCalendar(2003, 2, 3),
						new GregorianCalendar(2003, 2, 2), 0, 0, 'M', 'n', 0, null, null, "org.isf.utils.exception.OHServiceException" },
				/* 9 */ { true, "L00-L99 Skin and subcutaneous tissue", "Acne", new GregorianCalendar(2003, 2, 2), null,
						0, 0, 'M', 'n', 0, null, null, "org.isf.utils.exception.OHServiceException" },
				/* 10 */ { true, "L00-L99 Skin and subcutaneous tissue", "Acne", new GregorianCalendar(2003, 2, 2),
						new GregorianCalendar(2003, 2, 1), 0, 0, 'M', 'n', 0, null, null, "org.isf.utils.exception.OHServiceException" },
				/* 11 */ { true, "L00-L99 Skin and subcutaneous tissue", "Acne", new GregorianCalendar(2003, 2, 2),
						new GregorianCalendar(2003, 2, 2), null, 0, 'M', 'n', 0, null, null, "org.isf.utils.exception.OHServiceException" },
				/* 12 */ { true, "L00-L99 Skin and subcutaneous tissue", "Acne", new GregorianCalendar(2003, 2, 2),
						new GregorianCalendar(2003, 2, 2), 1, 0, 'M', 'n', 0, null, null, "org.isf.utils.exception.OHServiceException" },
				/* 13 */ { true, "L00-L99 Skin and subcutaneous tissue", "Acne", new GregorianCalendar(2003, 2, 2),
						new GregorianCalendar(2003, 2, 2), -1, 0, 'M', 'n', 0, null, null, "org.isf.utils.exception.OHServiceException" },
				/* 14 */ { true, "L00-L99 Skin and subcutaneous tissue", "Acne", new GregorianCalendar(2003, 2, 2),
						new GregorianCalendar(2003, 2, 2), 0, null, 'M', 'n', 0, null, null, "org.isf.utils.exception.OHServiceException" },
				/* 15 */ { true, "L00-L99 Skin and subcutaneous tissue", "Acne", new GregorianCalendar(2003, 2, 2),
						new GregorianCalendar(2003, 2, 2), 2, 0, 'M', 'n', 0, null, null, "org.isf.utils.exception.OHServiceException" },
				/* 16 */ { true, "L00-L99 Skin and subcutaneous tissue", "Acne", new GregorianCalendar(2003, 2, 2),
						new GregorianCalendar(2003, 2, 2), 0, -1, 'M', 'n', 0, null, null, "org.isf.utils.exception.OHServiceException" },
				/* 17 */ { true, "L00-L99 Skin and subcutaneous tissue", "Acne", new GregorianCalendar(2003, 2, 2),
						new GregorianCalendar(2003, 2, 2), 0, 0, null, 'n', 0, null, null, "org.isf.utils.exception.OHServiceException" },
				/* 18 */ { true, "J00-J99 Respiratory system",
						"Influenza with pneumonia, seazonal influenza virus detected",
						new GregorianCalendar(2010, 3, 9), new GregorianCalendar(2010, 3, 9), 31, 31, 'M', 'R', 0,
						new ArrayList<>(), new ArrayList<>(), null },
				/* 19 */ { true, "L00-L99 Skin and subcutaneous tissue", "Acne", new GregorianCalendar(2003, 2, 2),
						new GregorianCalendar(2003, 2, 2), 0, 0, 'F', 'n', 0, new ArrayList<>(), new ArrayList<>(),
						null },
				/* 20 */ { true, "L00-L99 Skin and subcutaneous tissue", "Acne", new GregorianCalendar(2003, 2, 2),
						new GregorianCalendar(2003, 2, 2), 0, 0, 'K', 'n', 0, null, null, "org.isf.utils.exception.OHServiceException" },
				/* 21 */ { true, "L00-L99 Skin and subcutaneous tissue", "Acne", new GregorianCalendar(2003, 2, 2),
						new GregorianCalendar(2003, 2, 2), 0, 0, 'M', null, 0, null, null, "org.isf.utils.exception.OHServiceException" },
				/* 22 */ { true, "J00-J99 Respiratory system",
						"Influenza with pneumonia, seazonal influenza virus detected",
						new GregorianCalendar(2010, 3, 9), new GregorianCalendar(2010, 3, 9), 31, 31, 'F', 'n', 0,
						new ArrayList<>(), new ArrayList<>(), null },
				/* 23 */ { true, "L00-L99 Skin and subcutaneous tissue", "Acne", new GregorianCalendar(2003, 2, 2),
						new GregorianCalendar(2003, 2, 2), 0, 0, 'M', 'R', 0, new ArrayList<>(), new ArrayList<>(),
						null },
				/* 24 */ { true, "L00-L99 Skin and subcutaneous tissue", "Acne", new GregorianCalendar(2003, 2, 2),
						new GregorianCalendar(2003, 2, 2), 0, 0, 'M', 'Q', 0, null, null, "org.isf.utils.exception.OHServiceException" } });
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
			diseaseIoOperationRepository.saveAndFlush(defaultData.getDefaultDiseases().get(i));
		}

		for (int i = 0; i < defaultData.getDefaultPatients().size(); i++) {
			patientIoOperationRepository.saveAndFlush(defaultData.getDefaultPatients().get(i));
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
					List<Opd> opds = disabled.getOpd(disTypeCode, disCode, dateFrom, dateTo, ageFrom, ageTo, sex,
							newPatient);
				} else {
					List<Opd> opds = opdManager.getOpd(disTypeCode, disCode, dateFrom, dateTo, ageFrom, ageTo, sex,
							newPatient);
				}
				fail("Expecting exception: " + expectedException);
			} catch (Exception e) {
				assertEquals(expectedException, e.getClass().getName());
			}
		} else {
			List<Opd> opds = opdManager.getOpd(disTypeCode, disCode, dateFrom, dateTo, ageFrom, ageTo, sex, newPatient);
			assertEquals(expectedSize, opds.size());
			for (int i = 0; i < opds.size(); i++) {
				assertEquals(expectedNames.get(i), opds.get(i).getfirstName());
				assertEquals(expectedCodes.get(i), opds.get(i).getCode());
			}
		}
	}
}
