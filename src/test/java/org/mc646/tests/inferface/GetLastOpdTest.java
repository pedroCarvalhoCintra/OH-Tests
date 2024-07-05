package org.mc646.tests.inferface;


import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertEquals;


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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.rules.SpringClassRule;
import org.springframework.test.context.junit4.rules.SpringMethodRule;

import java.util.Arrays;

@RunWith(Parameterized.class)
public class GetLastOpdTest extends OHCoreTestCase {
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
	public Integer patientCode;

	@Parameterized.Parameter(2)
	public String expectedName;

	@Parameterized.Parameter(3)
	public Integer expectedCode;

	@Parameterized.Parameter(4)
	public String expectedException;


	@Parameterized.Parameters
	public static Iterable<Object[]> data() {
		return Arrays.asList(new Object[][] {
			/* 0 */ { true, 1, "Bob", 1, null },
			/* 1 */ { false, 1, null, null, "java.lang.NullPointerException" },
			/* 2 */ { true, null, null, null, "org.isf.utils.exception.OHServiceException" },
			/* 3 */ { true, 0, null, null, "org.isf.utils.exception.OHServiceException" },
			/* 4 */ { true, 10, null, null, null } });
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

//	@Test
//	public void testCase1() throws Exception {
//		System.out.println("Test getLastOpd 1 running");
//		Opd result = opdManager.getLastOpd(1);
//
//		assertEquals(1, result.getCode());
//		assertEquals("Boob", result.getfirstName());
//		assertEquals(0, result.getAge());
//		assertEquals('M', result.getSex());
//		assertEquals("L00-L99 Skin and subcutaneous tissue", result.getDisease().getType().getCode());
//		assertEquals("Acne", result.getDisease().getCode());
//		assertEquals(0, result.getProgYear());
//		assertEquals(new GregorianCalendar(2003, 2, 2), result.getDate());
//	}
	@Test
	public void testCase() throws Exception {
		if (expectedException != null) {
			try {
				if (!databaseConnection) {
					OpdBrowserManager disabled = new OpdBrowserManager();
					Opd opd = disabled.getLastOpd(patientCode);
				} else {
					Opd opd = opdManager.getLastOpd(patientCode);
				}
				fail("Expecting exception: " + expectedException);
			} catch (Exception e) {
				assertEquals(expectedException, e.getClass().getName());
			}
		} else {
			Opd opd = opdManager.getLastOpd(patientCode);
			if (expectedCode == null) {
				assertEquals(null, opd);	
			} else {
				assertEquals(expectedName, opd.getfirstName());
				assertEquals(expectedCode, opd.getCode());	
			}
		}
	}


}
