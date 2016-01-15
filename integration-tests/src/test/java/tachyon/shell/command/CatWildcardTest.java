/*
 * Licensed to the University of California, Berkeley under one or more contributor license
 * agreements. See the NOTICE file distributed with this work for additional information regarding
 * copyright ownership. The ASF licenses this file to You under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance with the License. You may obtain a
 * copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */

package tachyon.shell.command;

import java.io.IOException;

import org.junit.Assert;
import org.junit.Test;

import tachyon.exception.TachyonException;
import tachyon.shell.AbstractTfsShellTest;
import tachyon.shell.TfsShellUtilsTest;
import tachyon.util.io.BufferUtils;

/**
 * Test used to cat by wildcard
 */
public class CatWildcardTest extends AbstractTfsShellTest {
  @Test
  public void catWildcardTest() throws IOException, TachyonException {
    TfsShellUtilsTest.resetTachyonFileHierarchy(mTfs);
    // the expect contents (remember that the order is based on path)
    byte[] exp1 = BufferUtils.getIncreasingByteArray(30); // testWildCards/bar/foobar3
    byte[] exp2 = BufferUtils.getIncreasingByteArray(10); // testWildCards/foo/foobar1
    byte[] exp3 = BufferUtils.getIncreasingByteArray(20); // testWildCards/foo/foobar2
    byte[] expect = new byte[exp1.length + exp2.length + exp3.length];
    System.arraycopy(exp1, 0, expect, 0, exp1.length);
    System.arraycopy(exp2, 0, expect, exp1.length, exp2.length);
    System.arraycopy(exp3, 0, expect, exp1.length + exp2.length, exp3.length);

    int ret = mFsShell.run("cat", "/testWildCards/*/foo*");
    Assert.assertEquals(0, ret);
    Assert.assertArrayEquals(mOutput.toByteArray(), expect);
  }
}
