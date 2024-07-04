package org.mc646.tests;

import org.graphwalker.core.condition.EdgeCoverage;
import org.graphwalker.core.condition.ReachedVertex;
import org.graphwalker.core.condition.TimeDuration;
import org.graphwalker.core.generator.AStarPath;
import org.graphwalker.core.generator.RandomPath;
import org.graphwalker.core.machine.ExecutionContext;
import org.graphwalker.core.model.Edge;
import org.graphwalker.java.test.TestBuilder;
import org.graphwalker.java.test.TestExecutor;
import org.junit.Test;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.TimeUnit;

public class OpdModelTest extends ExecutionContext implements IOpdModel {

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

//    @Test
//    public void runSmokeTest() {
//        new TestBuilder()
//                .addContext(new OpdModelTest().setNextElement(new Edge().setName("e_Init").build()),
//                        MODEL_PATH,
//                        new AStarPath(new ReachedVertex("v_Browse")))
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
//    @Test
//    public void runStabilityTest() {
//        new TestBuilder()
//                .addContext(new OpdModelTest().setNextElement(new Edge().setName("e_Init").build()),
//                        MODEL_PATH,
//                        new RandomPath(new TimeDuration(30, TimeUnit.SECONDS)))
//                .execute();
//    }
	
}
