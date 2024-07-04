package org.mc646.tests;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.evosuite.runtime.EvoAssertions.verifyException;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.GregorianCalendar;
import java.util.List;

import org.assertj.core.api.Condition;
import org.isf.OHCoreTestCase;
import org.isf.disease.model.Disease;
import org.isf.disease.service.DiseaseIoOperationRepository;
import org.isf.distype.model.DiseaseType;
import org.isf.distype.service.DiseaseTypeIoOperationRepository;
import org.isf.generaldata.GeneralData;
import org.isf.opd.manager.OpdBrowserManager;
import org.isf.opd.model.Opd;
import org.isf.opd.service.OpdIoOperationRepository;
import org.isf.opd.service.OpdIoOperations;
import org.isf.patient.model.Patient;
import org.isf.patient.model.PatientMergedEvent;
import org.isf.patient.service.PatientIoOperationRepository;
import org.isf.utils.exception.OHDataValidationException;
import org.isf.utils.exception.OHException;
import org.isf.utils.exception.OHServiceException;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.test.context.junit4.rules.SpringClassRule;
import org.springframework.test.context.junit4.rules.SpringMethodRule;

public class GetOpdOneWeekTest extends OHCoreTestCase {

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
	public void testCase1() throws Exception {
		System.out.println("Test 1 running.");
		System.out.println(diseaseTypeIoOperationRepository.findAll());
		System.out.println(diseaseIoOperationRepository.findAll());
		System.out.println(patientIoOperationRepository.findAll());

//		List<Opd> result = opdManager.getOpd(false);
//
//		for (int i = 0; i < result.size(); i++) {
//			System.out.println(result.get(i).getAge());
//		}
//		
//		assertTrue(result.size() == 1);
//		assertTrue(result.get(0).getPatient().getName() == "Daphine");
//		assertTrue(result.get(0).getAge() == 21);
//		assertTrue(result.get(0).getCode() == 3);
	}

	@Test
	public void testCase2() throws Exception {
		System.out.println("Test 2 running.");
//		List<Opd> result = opdManager.getOpd(false);
//		assertTrue(result.size() == 2);
//		assertTrue(result.get(0).getPatient().getName() == "Craig");
//		assertTrue(result.get(0).getAge() == 100);
//		assertTrue(result.get(0).getCode() == 2);
//		assertTrue(result.get(1).getPatient().getName() == "Daphine");
//		assertTrue(result.get(1).getAge() == 21);
//		assertTrue(result.get(1).getCode() == 3);
	}

	@Test(expected = NullPointerException.class)
	public void testCase3() throws Exception {
		System.out.println("Test 3 running.");
		OpdBrowserManager disconnectedManager = new OpdBrowserManager();
		disconnectedManager.getOpd(true);
	}

	@Test(expected = Error.class)
	public void testCase4() throws Exception {
		System.out.println("Test 4 running.");
		opdManager.getOpd(null);
	}
}
