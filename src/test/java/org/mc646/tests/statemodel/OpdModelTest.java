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
import org.isf.disease.service.DiseaseIoOperationRepository;
import org.isf.distype.service.DiseaseTypeIoOperationRepository;
import org.isf.opd.manager.OpdBrowserManager;
import org.isf.opd.service.OpdIoOperationRepository;
import org.isf.patient.service.PatientIoOperationRepository;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mc646.tests.inferface.DataSetUp;
import org.mc646.tests.inferface.DefaultDataSetUp;
import org.springframework.beans.factory.annotation.Autowired;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.TimeUnit;

public class OpdModelTest extends OHCoreTestCaseModel implements IOpdModel {
	public final static Path MODEL_PATH = Paths.get("org/mc646/tests/OPDModel.json");
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
	
	@Override
	public void v_OpdEditRegistration() {
		// TODO Auto-generated method stub

	}

	@Override
	public void e_OpdDelete() {
		// TODO Auto-generated method stub

	}

	@Override
	public void v_QuestionDelete() {
		// TODO Auto-generated method stub

	}

	@Override
	public void e_SaveOpd() {
		// TODO Auto-generated method stub

	}

	@Override
	public void e_SearchAllFields() {
		// TODO Auto-generated method stub

	}

	@Override
	public void e_DeleteYes() {
		// TODO Auto-generated method stub

	}

	@Override
	public void e_SearchOneWeek() {
		// TODO Auto-generated method stub

	}

	@Override
	public void e_OpdEdit() {
		// TODO Auto-generated method stub

	}

	@Override
	public void v_Opd() {
		// TODO Auto-generated method stub

	}

	@Override
	public void e_Cancel() {
		// TODO Auto-generated method stub

	}

	@Override
	public void e_DeleteNo() {
		// TODO Auto-generated method stub

	}

	@Override
	public void e_ValidData() {
		// TODO Auto-generated method stub

	}

	@Override
	public void e_InvalidData() {
		// TODO Auto-generated method stub

	}

	@Override
	public void e_SearchPatientID() {
		// TODO Auto-generated method stub

	}

	@Override
	public void e_OpdNew() {
		// TODO Auto-generated method stub

	}

	@Override
	public void v_OpdRegistration() {
		// TODO Auto-generated method stub

	}

	@Override
	public void e_NewEdge() {
		// TODO Auto-generated method stub

	}
	
	@Test
	public void test() {
		System.out.println("Running test");
	}

    @Test
    public void runSmokeTest() {
        new TestBuilder()
                .addContext(new OpdModelTest().setNextElement(new Edge().setName("e_Search").build()),
                        MODEL_PATH,
                        new AStarPath(new ReachedVertex("v_Opd")))
                .execute();
    }
//
//    @Test
//    public void runFunctionalTest() {
//        new TestBuilder()
//                .addContext(new OpdModelTest().setNextElement(new Edge().setName("e_Init").build()),
//                        MODEL_PATH,
//                        new RandomPath(new EdgeCoverage(100)))
//                .execute();
//    }
//
//    @Test
//    public void runStabilityTest() {
//        new TestBuilder()
//                .addContext(new OpdModelTest().setNextElement(new Edge().setName("e_Init").build()),
//                        MODEL_PATH,
//                        new RandomPath(new TimeDuration(30, TimeUnit.SECONDS)))
//                .execute();
//    }
	
}
