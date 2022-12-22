/*
 * SPDX-License-Identifier: Apache-2.0
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import proguard.classfile.editor.ClassBuilder
import proguard.classfile.io.ProgramClassWriter

import static proguard.classfile.AccessConstants.*
import static proguard.classfile.VersionConstants.CLASS_VERSION_1_8

var name = 'HelloProGuardCORE'
var superclass = 'java/lang/Object'
var classBuilder = new ClassBuilder(CLASS_VERSION_1_8, PUBLIC, name, superclass).tap {
    addMethod(PUBLIC | STATIC, 'main', '([Ljava/lang/String;)V', 100, builder ->
        builder
            .getstatic('java/lang/System', 'out', 'Ljava/io/PrintStream;')
            .ldc("Hello from $name")
            .invokevirtual('java/io/PrintStream', 'println', '(Ljava/lang/String;)V')
            .return_()
    )
}

new File("${name}.class").withDataOutputStream { dos ->
    classBuilder.programClass.accept(new ProgramClassWriter(dos))
}
