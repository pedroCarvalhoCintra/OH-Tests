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
import org.isf.opd.service.OpdIoOperationRepository;
import org.isf.patient.service.PatientIoOperationRepository;
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
import java.util.List;
import java.util.concurrent.TimeUnit;

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
		System.out.println("Abe");

	}

	@Override
	public void e_OpdDelete() {
		System.out.println("Abe");

	}

	@Override
	public void v_QuestionDelete() {
		System.out.println("Abe");

	}

	@Override
	public void e_SaveOpd() {
		System.out.println("Abe");

	}

	@Override
	public void e_SearchAllFields() {
		System.out.println("Abe");

	}

	@Override
	public void e_DeleteYes() {
		System.out.println("Abe");

	}

	@Override
	public void e_SearchOneWeek() {
		System.out.println("Abe");

	}

	@Override
	public void e_OpdEdit() {
		System.out.println("Abe");

	}

	@Override
	public void v_Opd() {
		System.out.println("Abe");

	}

	@Override
	public void e_Cancel() {
		System.out.println("Abe");

	}

	@Override
	public void e_DeleteNo() {
		System.out.println("Abe");

	}

	@Override
	public void e_ValidData() {
		System.out.println("Abe");

	}

	@Override
	public void e_InvalidData() {
		System.out.println("Abe");

	}

	@Override
	public void e_SearchPatientID() {
		System.out.println("Abe");

	}

	@Override
	public void e_OpdNew() {
		System.out.println("Abe");

	}

	@Override
	public void v_OpdRegistration() {
		System.out.println("Abe");

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
//    @Test
//    public void runFunctionalTest() {
//        new TestBuilder()
//                .addContext(new OpdModelTest().setNextElement(new Edge().setName("e_Init").build()),
//                        MODEL_PATH,
//                        new RandomPath(new EdgeCoverage(100)))
//                .execute();
//    }
//
    @Test
    public void runStabilityTest() {
        new TestBuilder()
                .addContext(new OpdModelTest().setNextElement(new Edge().setName("e_SearchOneWeek").build()),
                        MODEL_PATH,
                        new RandomPath(new TimeDuration(30, TimeUnit.SECONDS)))
                .execute();
    }
	
}
