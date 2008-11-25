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

import com.google.gwt.dev.GWTCompiler;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Project;
import org.apache.tools.ant.Task;
import org.apache.tools.ant.taskdefs.Execute;
import org.apache.tools.ant.taskdefs.Redirector;
import org.apache.tools.ant.types.CommandlineJava;
import org.apache.tools.ant.types.Path;

import java.io.File;
import java.io.IOException;

/**
 * Compiles a GWT module.
 */
public class GwtCompilerTask extends Task {
  private CommandlineJava command = new CommandlineJava();
  private String logLevel = "WARN";
  private String moduleFile;
  private String moduleName;
  private String out = "www";
  private String src = "src";
  private String style = "PRETTY";
  private String vmMaxMemory = "512m";

  public Path createClasspath() {
    return command.createClasspath(getProject());
  }

  /**
   * @see org.apache.tools.ant.Task#execute()
   */
  @Override
  public void execute() throws BuildException {
    final File srcDir = new File(src);

    if (moduleName == null) {
      if (moduleFile == null) {
        throw new BuildException("Required: either moduleName or moduleFile");
      } else {
        if (new File(moduleFile).exists()) {
          moduleName = convertModuleFileToName(srcDir, moduleFile);
        } else {
          throw new BuildException("FileTreeTableItem not found: " + moduleFile);
        }
      }
    }

    command.setClassname(GWTCompiler.class.getCanonicalName());
    command.createArgument().setLine("-logLevel " + logLevel);
    command.createArgument().setLine("-out " + out);
    command.createArgument().setLine("-style " + style);
    command.createArgument().setValue(moduleName);
    command.createVmArgument().setValue("-Xmx" + vmMaxMemory);

    log("Compiling " + moduleName, Project.MSG_INFO);

    final Redirector redirector = new Redirector(this);
    final Execute exe = new Execute(redirector.createHandler(), null);
    exe.setAntRun(getProject());
    exe.setCommandline(command.getCommandline());

    try {
      exe.setNewenvironment(true);
      exe.setVMLauncher(false);
      exe.execute();
      redirector.complete();
    } catch (IOException e) {
      throw new BuildException(e);
    }
  }

  public void setLogLevel(final String logLevel) {
    this.logLevel = logLevel.toUpperCase();
  }

  public void setModuleFile(final String moduleFile) {
    this.moduleFile = moduleFile;
  }

  public void setModuleName(final String moduleName) {
    this.moduleName = moduleName;
  }

  public void setOut(final String out) {
    this.out = out;
  }

  public void setSrc(final String src) {
    this.src = src;
  }

  public void setStyle(final String style) {
    this.style = style.toUpperCase();
  }

  public void setVmMaxMemory(final String vmMaxMemory) {
    this.vmMaxMemory = vmMaxMemory;
  }

  private String convertModuleFileToName(final File srcDir,
      final String moduleFile) {
    log("Converting file name " + moduleFile
        + " to a module name relative to source root "
        + srcDir.getAbsolutePath(), Project.MSG_DEBUG);
    return moduleFile.replace(File.separatorChar, '.').substring(
        srcDir.getAbsolutePath().length() + 1, moduleFile.length() - 8);
  }
}
