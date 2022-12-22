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

import net.bytebuddy.ByteBuddy
import net.bytebuddy.implementation.MethodCall

import static java.lang.reflect.Modifier.*

var name = 'HelloByteBuddy'
new ByteBuddy()
    .subclass(Object)
    .name(name)
    .defineMethod('main', Void.TYPE, PUBLIC | STATIC)
    .withParameter(String[])
    .intercept(MethodCall.invoke(
        PrintStream.getMethod('println', String))
        .onField(System.getField('out'))
        .with('Hello from ' + name))
    .make()
    .saveIn('.' as File)
