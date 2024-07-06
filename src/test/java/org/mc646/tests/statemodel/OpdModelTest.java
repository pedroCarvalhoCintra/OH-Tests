package org.mc646.tests.statemodel;

import org.graphwalker.core.condition.EdgeCoverage;
import org.graphwalker.core.condition.ReachedVertex;
import org.graphwalker.core.condition.TimeDuration;
import org.graphwalker.core.generator.AStarPath;
import org.graphwalker.core.generator.RandomPath;
import org.graphwalker.core.machine.ExecutionContext;
import org.graphwalker.core.model.Edge;
import org.graphwalker.java.test.TestBuilder;
import org.graphwalker.java.test.TestExecutor;
import org.isf.OHCoreTestCase;
import org.isf.OHCoreTestCaseModel;
import org.isf.disease.service.DiseaseIoOperationRepository;
import org.isf.distype.service.DiseaseTypeIoOperationRepository;
import org.isf.opd.manager.OpdBrowserManager;
import org.isf.opd.model.Opd;
import org.isf.opd.service.OpdIoOperationRepository;
import org.isf.patient.service.PatientIoOperationRepository;
import org.isf.utils.exception.OHServiceException;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mc646.tests.inferface.DataSetUp;
import org.mc646.tests.inferface.DefaultDataSetUp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.*;

public class OpdModelTest extends OHCoreTestCaseModel implements IOpdModel {
	public final static Path MODEL_PATH = Paths.get("org/mc646/tests/statemodel/OPDModel.json");
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

	static int progYear;
	static int count;
	static List<Opd> opds;
	static boolean operationOpdSaved;
	static boolean operationOpdUpdated;
	static boolean operationOpdDeleted;
	static Opd opdCreate;
	static Opd opdUpdate;
	static Opd opdDelete;
	static boolean valid = false;
	static boolean searched = false;


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
			count++;
		}

		progYear = 0;
		count = 0;
		valid = false;
		searched = false;

	}

	@After
	public void tearDown() throws Exception {
		cleanH2InMemoryDb();
	}

	@Override
	public void v_OpdEditRegistration() {
		assertTrue(searched);
		assertTrue(count > 0);
		opdUpdate = opds.get(0);
		opdUpdate.setProgYear(++progYear);
	}

	@Override
	public void e_OpdDelete() {
	}

	@Override
	public void v_QuestionDelete() {
		assertTrue(searched);
		assertTrue(count > 0);
		opdDelete = opds.get(0);
	}

	@Override
	public void e_SaveOpd() {
		if (valid) {
			try {
				opdManager.newOpd(opdCreate);
				count++;
				opds.add(opdCreate);
				operationOpdSaved = true;
			} catch (OHServiceException e) {
				throw new RuntimeException(e);
			}
		}
	}

	@Override
	public void e_SaveEditOpd() {
		if ( valid ) {
			try {
				opds.set(0, opdManager.updateOpd(opdUpdate));
				operationOpdUpdated = true;
			} catch (OHServiceException e) {
				throw new RuntimeException(e);
			}
		}
	}

	@Override
	public void e_SearchAllFields() {
		searched = true;

		String diseaseTypeCode = "L00-L99 Skin and subcutaneous tissue";
		String diseaseCode = "Acne";
		GregorianCalendar dateFrom = new GregorianCalendar(2003, 0, 0);
		GregorianCalendar dateTo = new GregorianCalendar(2005, 0, 0);
		int ageFrom = 0;
		int ageTo = 100;
		char sex = 'M';
		char newPatient = 'n';

		try {
			opds = opdManager.getOpd(diseaseTypeCode, diseaseCode, dateFrom, dateTo, ageFrom, ageTo, sex, newPatient);
		} catch (OHServiceException e) {
			throw new RuntimeException(e);
		}

		count = opds.size();
	}

	@Override
	public void e_DeleteYes() {
		try {
			opdManager.deleteOpd(opdDelete);
			opds.remove(opdDelete);
			count--;
			operationOpdDeleted = true;
		} catch (OHServiceException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void e_SearchOneWeek() {
		searched = true;

		boolean ondeWeek = true;
		List<Opd> opds = null;
		try {
			opds = opdManager.getOpd(ondeWeek);
		} catch (OHServiceException e) {
			throw new RuntimeException(e);
		}

		count = opds.size();
	}

	@Override
	public void e_OpdEdit() {


	}

	@Override
	public void v_Opd() {
		// init
		if ( operationOpdSaved ) {
			boolean opdSaved = false;
			List<Opd> opdsDb =  opdIoOperationRepository.findAll();
			for (Opd opd : opdsDb) {
				if (opd == opdCreate) {
					opdSaved = true;
					break;
				}
			}
			assertTrue(opdSaved);
			operationOpdSaved = false;
		} else if ( operationOpdUpdated ) {
			boolean opdUpdated = false;
			List<Opd> opdsDb =  opdIoOperationRepository.findAll();
			for (Opd opd : opdsDb) {
				if (opd == opdUpdate) {
					opdUpdated = true;
					break;
				}
			}
			assertTrue(opdUpdated);
			operationOpdUpdated = false;
		} else if ( operationOpdDeleted ) {
			boolean opdDeleted = true;
			List<Opd> opdsDb =  opdIoOperationRepository.findAll();
			for (Opd opd : opdsDb) {
				if (opd == opdDelete) {
					opdDeleted = false;
					break;
				}
			}
			assertTrue(opdDeleted);
			operationOpdDeleted = false;
		}
	}

	@Override
	public void e_Cancel() {
		// back to v_opd;
		opdUpdate = null;
		opdCreate = null;
		operationOpdSaved = false;
		operationOpdUpdated = false;
	}

	@Override
	public void e_DeleteNo() {
		// back to v_opd
		opdDelete = null;
		operationOpdDeleted = false;
	}

	@Override
	public void e_ValidData() {
		valid = true;
	}

	@Override
	public void e_InvalidData() {
		valid = false;
	}

	@Override
	public void e_SearchPatientID() {
		searched = true;

		int patientId = 1;
		List<Opd> opds = null;
		try {
			opds = opdManager.getOpdList(patientId);
		} catch (OHServiceException e) {
			throw new RuntimeException(e);
		}

		count = opds.size();
	}

	@Override
	public void e_OpdNew() {
		// go to OpdRegistration
	}

	@Override
	public void v_OpdRegistration() {
		// invalid or valid
		if ( valid ) {
			opdCreate = dataSetUp.newOpd( ++progYear, 'M', 3, defaultData.getDefaultDiseases().get(0), null, null, new GregorianCalendar(), 'R', "note", defaultData.getDefaultPatients().get(8));
		} else {
			opdCreate = dataSetUp.newOpd(++progYear, 'K', 3, defaultData.getDefaultDiseases().get(0), null, null, new GregorianCalendar(), 'R', "note", defaultData.getDefaultPatients().get(8));
		}

	}
	
	@Test
	public void test() {
		System.out.println("Running test");
	}

//    @Test
//    public void runSmokeTest() {
//        new TestBuilder()
//                .addContext(new OpdModelTest().setNextElement(new Edge().setName("e_SearchOneWeek").build()),
//                        MODEL_PATH,
//                        new AStarPath(new ReachedVertex("v_Opd")))
//                .execute();
//    }
//
    @Test
    public void runFunctionalTest() {
        new TestBuilder()
                .addContext(new OpdModelTest().setNextElement(new Edge().setName("e_Init").build()),
                        MODEL_PATH,
                        new RandomPath(new EdgeCoverage(100)))
                .execute();
    }
//
//    @Test
//    public void runStabilityTest() {
//        new TestBuilder()
//                .addContext(new OpdModelTest().setNextElement(new Edge().setName("e_SearchOneWeek").build()),
//                        MODEL_PATH,
//                        new RandomPath(new TimeDuration(30, TimeUnit.SECONDS)))
//                .execute();
//    }
	
}
