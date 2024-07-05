package org.mc646.tests.inferface;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.junit.jupiter.api.Assertions.assertEquals;

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
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.test.context.junit4.rules.SpringClassRule;
import org.springframework.test.context.junit4.rules.SpringMethodRule;

@RunWith(Parameterized.class)
public class GetOpdOneWeekTest extends OHCoreTestCase {
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
	public Boolean oneWeek;

	@Parameter(2)
	public int expectedSize;

	@Parameter(3)
	public List<String> expectedNames;

	@Parameter(4)
	public List<Integer> expectedCodes;

	@Parameter(5)
	public String expectedException;

	@Parameters
	public static Iterable<Object[]> data() {
		return Arrays.asList(new Object[][] {
				/* 0 */ { true, true, 2, new ArrayList<>(Arrays.asList("Craig", "Daphine")),
						new ArrayList<>(Arrays.asList(3, 4)), null },
				/* 1 */ { true, false, 1, new ArrayList<>(Arrays.asList("Daphine")), new ArrayList<>(Arrays.asList(4)),
						null },
				/* 2 */ { false, true, 0, null, null, "java.lang.NullPointerException" },
				/* 3 */ { true, null, 0, null, null, "org.isf.utils.exception.OHServiceException" } });
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
					List<Opd> opds = disabled.getOpd(oneWeek);
				} else {
					List<Opd> opds = opdManager.getOpd(oneWeek);
				}
				fail("Expecting exception: " + expectedException);
			} catch (Exception e) {
				assertEquals(expectedException, e.getClass().getName());
			}
		} else {
			List<Opd> opds = opdManager.getOpd(oneWeek);
			assertEquals(expectedSize, opds.size());
			for (int i = 0; i < opds.size(); i++) {
				assertEquals(expectedNames.get(i), opds.get(i).getfirstName());
				assertEquals(expectedCodes.get(i), opds.get(i).getCode());
			}
		}
	}
}
