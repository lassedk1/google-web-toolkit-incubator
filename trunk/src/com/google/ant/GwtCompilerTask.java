/*
 * Copyright 2008 Google Inc.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.google.ant;

import com.google.gwt.core.ext.UnableToCompleteException;
import com.google.gwt.core.ext.TreeLogger.Type;
import com.google.gwt.dev.GWTCompiler;
import com.google.gwt.dev.cfg.ModuleDef;
import com.google.gwt.dev.cfg.ModuleDefLoader;
import com.google.gwt.dev.util.log.PrintWriterTreeLogger;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Task;

import java.io.File;

/**
 * Compiles a GWT module.
 */
public class GwtCompilerTask extends Task {
  private String src = "src";
  private String out = "www";
  private String moduleFile;
  private String moduleName;

  /**
   * @see org.apache.tools.ant.Task#execute()
   */
  @Override
  public void execute() throws BuildException {
    final File srcDir = new File(src);

    if (moduleName == null) {
      moduleName = convertModuleFileToName(srcDir, moduleFile);
    }

    System.out.println("Compiling " + moduleName);

    final GWTCompiler compiler = new GWTCompiler();

    compiler.setModuleName(moduleName);
    compiler.setLogLevel(Type.ERROR);
    compiler.setOutDir(new File(out));
    compiler.setStylePretty();

    final PrintWriterTreeLogger logger = new PrintWriterTreeLogger();
    logger.setMaxDetail(compiler.getLogLevel());

    try {
      final ModuleDef moduleDef = ModuleDefLoader.loadFromClassPath(logger,
          moduleName);
      compiler.distill(logger, moduleDef);
    } catch (UnableToCompleteException e) {
      throw new BuildException(e);
    }
  }

  private String convertModuleFileToName(final File srcDir,
      final String moduleFile) {
    return moduleFile.replace(File.separatorChar, '.').substring(
        srcDir.getAbsolutePath().length() + 1, moduleFile.length() - 8);
  }

  public void setSrc(final String src) {
    this.src = src;
  }

  public void setOut(final String out) {
    this.out = out;
  }

  public void setModuleFile(final String moduleFile) {
    this.moduleFile = moduleFile;
  }

  public void setModuleName(final String moduleName) {
    this.moduleName = moduleName;
  }
}
