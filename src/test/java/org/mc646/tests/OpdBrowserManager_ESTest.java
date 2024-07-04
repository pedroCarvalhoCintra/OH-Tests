/*
 * This file was automatically generated by EvoSuite
 * Mon Jul 01 16:46:27 GMT 2024
 */

package org.mc646.tests;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.util.GregorianCalendar;
import java.util.Locale;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.evosuite.runtime.mock.java.util.MockGregorianCalendar;
import org.isf.disease.model.Disease;
import org.isf.distype.model.DiseaseType;
import org.isf.generaldata.GeneralData;
import org.isf.opd.manager.OpdBrowserManager;
import org.isf.opd.model.Opd;
import org.junit.runner.RunWith;

//@RunWith(EvoRunner.class) @EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = true) 
public class OpdBrowserManager_ESTest extends OpdBrowserManager_ESTest_scaffolding {

  @Test(timeout = 4000)
  public void test00()  throws Throwable  {
      OpdBrowserManager opdBrowserManager0 = new OpdBrowserManager();
      Opd opd0 = new Opd();
      DiseaseType diseaseType0 = new DiseaseType();
      Disease disease0 = new Disease("G", "G", diseaseType0);
      opd0.setDisease(disease0);
      MockGregorianCalendar mockGregorianCalendar0 = new MockGregorianCalendar();
      opd0.setDate(mockGregorianCalendar0);
      // Undeclared exception!
      try {
        opdBrowserManager0.newOpd(opd0);
        fail("Expecting exception: NullPointerException");

      } catch(NullPointerException e) {
         //
         // no message in exception (getMessage() returned null)
         //
         verifyException("org.isf.opd.manager.OpdBrowserManager", e);
      }
  }

  @Test(timeout = 4000)
  public void test01()  throws Throwable  {
      OpdBrowserManager opdBrowserManager0 = new OpdBrowserManager();
      DiseaseType diseaseType0 = new DiseaseType();
      Disease disease0 = new Disease();
      Opd opd0 = new Opd(4, '4', 4, disease0);
      disease0.setCode("dO");
      Disease disease1 = new Disease("angal.opd.specifyingduplicatediseasesisnotallowed.msg", "dO", diseaseType0);
      opd0.setDisease3(disease1);
      opd0.setDisease2(disease0);
      try {
        opdBrowserManager0.newOpd(opd0);
        fail("Expecting exception: Exception");

      } catch(Exception e) {
         //
         // no message in exception (getMessage() returned null)
         //
         verifyException("org.isf.opd.manager.OpdBrowserManager", e);
      }
  }

  @Test(timeout = 4000)
  public void test02()  throws Throwable  {
      OpdBrowserManager opdBrowserManager0 = new OpdBrowserManager();
      Disease disease0 = new Disease();
      Opd opd0 = new Opd(4, '4', 4, disease0);
      disease0.setCode("dO");
      opd0.setDisease3(disease0);
      opd0.setDisease2(disease0);
      try {
        opdBrowserManager0.newOpd(opd0);
        fail("Expecting exception: Exception");

      } catch(Exception e) {
         //
         // no message in exception (getMessage() returned null)
         //
         verifyException("org.isf.opd.manager.OpdBrowserManager", e);
      }
  }

  @Test(timeout = 4000)
  public void test03()  throws Throwable  {
      OpdBrowserManager opdBrowserManager0 = new OpdBrowserManager();
      DiseaseType diseaseType0 = new DiseaseType();
      Disease disease0 = new Disease("EEE MMM dd hh:mm:ss z yyyy", "EEE MMM dd hh:mm:ss z yyyy", diseaseType0);
      Opd opd0 = new Opd((-841), '&', (-841), disease0);
      Disease disease1 = new Disease();
      opd0.setDisease2(disease1);
      try {
        opdBrowserManager0.newOpd(opd0);
        fail("Expecting exception: Exception");

      } catch(Exception e) {
         //
         // no message in exception (getMessage() returned null)
         //
         verifyException("org.isf.opd.manager.OpdBrowserManager", e);
      }
  }

  @Test(timeout = 4000)
  public void test04()  throws Throwable  {
      OpdBrowserManager opdBrowserManager0 = new OpdBrowserManager();
      Opd opd0 = new Opd();
      MockGregorianCalendar mockGregorianCalendar0 = new MockGregorianCalendar();
      opd0.setDate(mockGregorianCalendar0);
      try {
        opdBrowserManager0.newOpd(opd0);
        fail("Expecting exception: Exception");

      } catch(Exception e) {
         //
         // no message in exception (getMessage() returned null)
         //
         verifyException("org.isf.opd.manager.OpdBrowserManager", e);
      }
  }

  @Test(timeout = 4000)
  public void test05()  throws Throwable  {
      OpdBrowserManager opdBrowserManager0 = new OpdBrowserManager();
      Opd opd0 = new Opd();
      opd0.setUserID("Da+RRjk) h)Y`{<> n");
      try {
        opdBrowserManager0.newOpd(opd0);
        fail("Expecting exception: Exception");

      } catch(Exception e) {
         //
         // no message in exception (getMessage() returned null)
         //
         verifyException("org.isf.opd.manager.OpdBrowserManager", e);
      }
  }

  @Test(timeout = 4000)
  public void test06()  throws Throwable  {
      OpdBrowserManager opdBrowserManager0 = new OpdBrowserManager();
      Opd opd0 = new Opd();
      GeneralData.OPDEXTENDED = true;
      try {
        opdBrowserManager0.newOpd(opd0);
        fail("Expecting exception: Exception");

      } catch(Exception e) {
         //
         // no message in exception (getMessage() returned null)
         //
         verifyException("org.isf.opd.manager.OpdBrowserManager", e);
      }
  }

  @Test(timeout = 4000)
  public void test07()  throws Throwable  {
      OpdBrowserManager opdBrowserManager0 = new OpdBrowserManager();
      // Undeclared exception!
      try {
        opdBrowserManager0.getProgYear(32);
        fail("Expecting exception: NullPointerException");

      } catch(NullPointerException e) {
         //
         // no message in exception (getMessage() returned null)
         //
         verifyException("org.isf.opd.manager.OpdBrowserManager", e);
      }
  }

  @Test(timeout = 4000)
  public void test08()  throws Throwable  {
      OpdBrowserManager opdBrowserManager0 = new OpdBrowserManager();
      // Undeclared exception!
      try {
        opdBrowserManager0.getOpd(true);
        fail("Expecting exception: NullPointerException");

      } catch(NullPointerException e) {
         //
         // no message in exception (getMessage() returned null)
         //
         verifyException("org.isf.opd.manager.OpdBrowserManager", e);
      }
  }

  @Test(timeout = 4000)
  public void test09()  throws Throwable  {
      OpdBrowserManager opdBrowserManager0 = new OpdBrowserManager();
      Disease disease0 = new Disease();
      Opd opd0 = new Opd((-548), ' ', (-548), disease0);
      try {
        opdBrowserManager0.updateOpd(opd0);
        fail("Expecting exception: Exception");

      } catch(Exception e) {
         //
         // no message in exception (getMessage() returned null)
         //
         verifyException("org.isf.opd.manager.OpdBrowserManager", e);
      }
  }

  @Test(timeout = 4000)
  public void test10()  throws Throwable  {
      OpdBrowserManager opdBrowserManager0 = new OpdBrowserManager();
      // Undeclared exception!
      try {
        opdBrowserManager0.getOpdList(51);
        fail("Expecting exception: NullPointerException");

      } catch(NullPointerException e) {
         //
         // no message in exception (getMessage() returned null)
         //
         verifyException("org.isf.opd.manager.OpdBrowserManager", e);
      }
  }

  @Test(timeout = 4000)
  public void test11()  throws Throwable  {
      OpdBrowserManager opdBrowserManager0 = new OpdBrowserManager();
      Locale locale0 = Locale.US;
      MockGregorianCalendar mockGregorianCalendar0 = new MockGregorianCalendar(locale0);
      // Undeclared exception!
      try {
        opdBrowserManager0.getOpd("angal.common.pleaseselectapatient.msg", "angal.common.pleaseselectapatient.msg", (GregorianCalendar) mockGregorianCalendar0, (GregorianCalendar) mockGregorianCalendar0, 0, 0, '\\', '\\');
        fail("Expecting exception: NullPointerException");

      } catch(NullPointerException e) {
         //
         // no message in exception (getMessage() returned null)
         //
         verifyException("org.isf.opd.manager.OpdBrowserManager", e);
      }
  }

  @Test(timeout = 4000)
  public void test12()  throws Throwable  {
      OpdBrowserManager opdBrowserManager0 = new OpdBrowserManager();
      // Undeclared exception!
      try {
        opdBrowserManager0.getLastOpd(0);
        fail("Expecting exception: NullPointerException");

      } catch(NullPointerException e) {
         //
         // no message in exception (getMessage() returned null)
         //
         verifyException("org.isf.opd.manager.OpdBrowserManager", e);
      }
  }

  @Test(timeout = 4000)
  public void test13()  throws Throwable  {
      OpdBrowserManager opdBrowserManager0 = new OpdBrowserManager();
      // Undeclared exception!
      try {
        opdBrowserManager0.isExistOpdNum(1238, 1238);
        fail("Expecting exception: NullPointerException");

      } catch(NullPointerException e) {
         //
         // no message in exception (getMessage() returned null)
         //
         verifyException("org.isf.opd.manager.OpdBrowserManager", e);
      }
  }

  @Test(timeout = 4000)
  public void test14()  throws Throwable  {
      OpdBrowserManager opdBrowserManager0 = new OpdBrowserManager();
      Opd opd0 = new Opd();
      // Undeclared exception!
      try {
        opdBrowserManager0.deleteOpd(opd0);
        fail("Expecting exception: NullPointerException");

      } catch(NullPointerException e) {
         //
         // no message in exception (getMessage() returned null)
         //
         verifyException("org.isf.opd.manager.OpdBrowserManager", e);
      }
  }
}