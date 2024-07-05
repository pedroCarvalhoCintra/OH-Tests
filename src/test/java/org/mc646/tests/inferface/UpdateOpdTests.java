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
public class UpdateOpdTests extends OHCoreTestCase {
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
	public Integer opdId;

	@Parameter(2)
	public String diseaseId;

	@Parameter(3)
	public String disease2Id;

	@Parameter(4)
	public String disease3Id;

	@Parameter(5)
	public Integer patientId;
	
	@Parameter(6)
	public GregorianCalendar date;
	
	@Parameter(7)
	public Integer age;

	@Parameter(8)
	public Character sex;
	
	@Parameter(9)
	public Character newPatient;
	
	@Parameter(10)
	public String notes;

	@Parameter(11)
	public String expectedException;

	@Parameters
	public static Iterable<Object[]> data() {
		return Arrays.asList(new Object[][] {
				/* 0 */ { true, 1, "Acne", null, null, 1, new GregorianCalendar(2003, 02, 20), 0, 'M', 'n', "", null},
				/* 1 */ { false, 1, "Acne", null, null, 1, new GregorianCalendar(2003, 02, 20), 0, 'M', 'n', "", "java.lang.NullPointerException"},
				/* 2 */ { true, null, "Acne", null, null, 1, new GregorianCalendar(2003, 02, 20), 0, 'M', 'n', "", "org.isf.utils.exception.OHDataValidationException"},
				/* 3 */ { true, 1, "Acne", null, null, 1, null, 0, 'M', 'n', "", "org.isf.utils.exception.OHDataValidationException"},
				/* 4 */ { true, 1, "Acne", null, null, -1, new GregorianCalendar(2003, 02, 20), 0, 'M', 'n', "", "org.isf.utils.exception.OHDataValidationException"},
				/* 5 */ { true, 1, "Acne", null, null, 1, new GregorianCalendar(2003, 02, 20), -1, 'M', 'n', "", "org.isf.utils.exception.OHDataValidationException"},
				/* 6 */ { true, 1, "Acne", null, null, 1, new GregorianCalendar(2003, 02, 20), 0, 'K', 'n', "", "org.isf.utils.exception.OHDataValidationException"},
				/* 7 */ { true, 1, null, null, null, 1, new GregorianCalendar(2003, 02, 20), 0, 'M', 'n', "", "org.isf.utils.exception.OHDataValidationException"},
				/* 8 */ { true, 1, "Acne", "Acne", null, 1, new GregorianCalendar(2003, 02, 20), 0, 'M', 'n', "", "org.isf.utils.exception.OHDataValidationException"},
				/* 9 */ { true, 1, "Acne", null, "Acne", 1, new GregorianCalendar(2003, 02, 20), 0, 'M', 'n', "", "org.isf.utils.exception.OHDataValidationException"},
				/* 10 */ { true, 1, "Acne", null, null, 1, new GregorianCalendar(2003, 02, 20), 0, 'M', 'L', "", "org.isf.utils.exception.OHDataValidationException"},
				/* 11 */ { true, 1, "Acne", null, null, 1, new GregorianCalendar(2003, 02, 20), 0, 'M', 'n', null, "org.isf.utils.exception.OHDataValidationException"},
				/* 12 */ { true, 1, "Acne", null, null, 1, new GregorianCalendar(2003, 02, 20), 0, 'M', 'n', "", "org.isf.utils.exception.OHDataValidationException"},
				/* 13 */ { true, -1, "Acne", null, null, 1, new GregorianCalendar(2003, 02, 20), 0, 'M', 'n', "", "org.isf.utils.exception.OHServiceException"},
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
			Opd opd = opdIoOperationRepository.getOne(opdId);
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
