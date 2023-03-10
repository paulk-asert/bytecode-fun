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

import org.objectweb.asm.ClassWriter

import static org.objectweb.asm.Opcodes.*

var name = 'HelloASM'
var superclass = 'java/lang/Object'
var cw = new ClassWriter(0)
cw.visit(V1_8, ACC_PUBLIC + ACC_SUPER, name, null, superclass, null)
cw.visitMethod(ACC_PUBLIC + ACC_STATIC, 'main', '([Ljava/lang/String;)V', null, null).with {
    visitCode()
    visitFieldInsn(GETSTATIC, 'java/lang/System', 'out', 'Ljava/io/PrintStream;')
    visitLdcInsn('Hello from ' + name)
    visitMethodInsn(INVOKEVIRTUAL, 'java/io/PrintStream', 'println', '(Ljava/lang/String;)V', false)
    visitInsn(RETURN)
    visitMaxs(3, 3)
    visitEnd()
}
cw.visitEnd()

new File("${name}.class").withDataOutputStream { dos ->
    dos.write(cw.toByteArray())
}
