package org.mc646.tests.inferface;

import static org.junit.Assert.fail;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.GregorianCalendar;
import java.util.List;

import org.isf.OHCoreTestCase;
import org.isf.disease.service.DiseaseIoOperationRepository;
import org.isf.distype.service.DiseaseTypeIoOperationRepository;
import org.isf.opd.manager.OpdBrowserManager;
import org.isf.opd.model.Opd;
import org.isf.opd.service.OpdIoOperationRepository;
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
public class NewOpdTest extends OHCoreTestCase {
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
	public Opd opd;

	@Parameter(2)
	public String diseaseId;

	@Parameter(3)
	public String disease2Id;

	@Parameter(4)
	public String disease3Id;

	@Parameter(5)
	public int patientId;

	@Parameter(6)
	public boolean expectedResult;

	@Parameter(7)
	public int expectedSize;

	@Parameter(8)
	public List<Integer> expectedCodes;

	@Parameter(9)
	public String expectedException;

	@Parameters
	public static Iterable<Object[]> data() {
		return Arrays.asList(new Object[][] {
				/* 0 */ {
						true,
						dataSetUp.newOpd(1, 'M', 0, null, null, null, new GregorianCalendar(2010, 02, 02), 'n', "",
								null),
						"Acne", null, null, 1, true, 5, new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5)), null },
				/* 1 */ { false,
						dataSetUp.newOpd(1, 'M', 0, null, null, null, new GregorianCalendar(2010, 02, 02), 'n', "",
								null),
						"Acne", null, null, 1, false, 0, null, "java.lang.NullPointerException" },
				/* 2 */ { true, null, null, null, null, -1, false, 0, null, "java.lang.NullPointerException" },
				/* 3 */ { true, dataSetUp.newOpd(1, 'M', 0, null, null, null, null, 'n', "", null), "Acne", null, null,
						1, false, 0, null, "org.isf.utils.exception.OHDataValidationException" },
				/* 4 */ { true,
						dataSetUp.newOpd(1, 'M', 0, null, null, null, new GregorianCalendar(2010, 02, 02), 'n', "",
								null),
						"Acne", null, null, -1, false, 0, null,
						"org.isf.utils.exception.OHDataValidationException" },
				/* 5 */ { true,
						dataSetUp.newOpd(1, 'M', -1, null, null, null, new GregorianCalendar(2010, 02, 02), 'n', "",
								null),
						"Acne", null, null, 1, false, 0, null, "org.isf.utils.exception.OHDataValidationException" },
				/* 6 */ { true,
						dataSetUp.newOpd(1, 'K', 0, null, null, null, new GregorianCalendar(2010, 02, 02), 'n', "",
								null),
						"Acne", null, null, 1, false, 0, null, "org.isf.utils.exception.OHDataValidationException" },
				/* 7 */ { true,
						dataSetUp.newOpd(1, 'M', 0, null, null, null, new GregorianCalendar(2010, 02, 02), 'n', "",
								null),
						null, null, null, 1, false, 0, null, "org.isf.utils.exception.OHDataValidationException" },
				/* 8 */ { true,
						dataSetUp.newOpd(1, 'M', 0, null, null, null, new GregorianCalendar(2010, 02, 02), 'n', "",
								null),
						"Acne", "Acne", null, 1, false, 0, null,
						"org.isf.utils.exception.OHDataValidationException" },
				/* 9 */ { true,
						dataSetUp.newOpd(1, 'M', 0, null, null, null, new GregorianCalendar(2010, 02, 02), 'n', "",
								null),
						"Acne", null, "Acne", 1, false, 0, null,
						"org.isf.utils.exception.OHDataValidationException" },
				/* 10 */ { true,
						dataSetUp.newOpd(1, 'M', 0, null, null, null, new GregorianCalendar(2010, 02, 02), 'L', "",
								null),
						"Acne", null, null, 1, false, 0, null, "org.isf.utils.exception.OHDataValidationException" },
				/* 11 */ { true,
						dataSetUp.newOpd(1, 'M', 0, null, null, null, new GregorianCalendar(2010, 02, 02), 'n', null,
								null),
						"Acne", null, null, 1, false, 0, null, "org.isf.utils.exception.OHDataValidationException" },
				/* 12 */ { true,
						dataSetUp.newOpd(1, 'K', 0, null, null, null, new GregorianCalendar(2010, 02, 02), 'Q', "",
								null),
						"Acne", null, null, 1, false, 0, null, "org.isf.utils.exception.OHDataValidationException" },

		});
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
				opd.setDisease(diseaseId == null ? null : diseaseIoOperationRepository.findOneByCode(diseaseId));
				opd.setDisease2(disease2Id == null ? null : diseaseIoOperationRepository.findOneByCode(diseaseId));
				opd.setDisease3(disease3Id == null ? null : diseaseIoOperationRepository.findOneByCode(diseaseId));
				opd.setPatient(patientId == -1 ? null : patientIoOperationRepository.findOne(patientId));
				if (!databaseConnection) {
					OpdBrowserManager disabled = new OpdBrowserManager();
					boolean result = disabled.newOpd(opd);
				} else {
					boolean result = opdManager.newOpd(opd);
				}
				fail("Expecting exception: " + expectedException);
			} catch (Exception e) {
				assertEquals(expectedException, e.getClass().getName());
			}
		} else {
			opd.setDisease(diseaseId == null ? null : diseaseIoOperationRepository.findOneByCode(diseaseId));
			opd.setDisease2(disease2Id == null ? null : diseaseIoOperationRepository.findOneByCode(diseaseId));
			opd.setDisease3(disease3Id == null ? null : diseaseIoOperationRepository.findOneByCode(diseaseId));
			opd.setPatient(patientId == -1 ? null : patientIoOperationRepository.findOne(patientId));
			boolean result = opdManager.newOpd(opd);
			assertEquals(expectedResult, result);
			List<Opd> opds = opdIoOperationRepository.findAll();
			assertEquals(expectedSize, opds.size());
			for (int i = 0; i < opds.size(); i++) {
				assertEquals(expectedCodes.get(i), opds.get(i).getCode());
			}
		}
	}
}
