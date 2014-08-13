/* 
 * VisualCodeBuilder.java
 *
 * Copyright (c) 2009–2014 Steinbeis Forschungszentrum (STZ Ölbronn),
 * Copyright (c) 2006–2014 by Michael Hoffer
 * 
 * This file is part of Visual Reflection Library (VRL).
 *
 * VRL is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License version 3
 * as published by the Free Software Foundation.
 * 
 * see: http://opensource.org/licenses/LGPL-3.0
 *      file://path/to/VRL/src/eu/mihosoft/vrl/resources/license/lgplv3.txt
 *
 * VRL is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * This version of VRL includes copyright notice and attribution requirements.
 * According to the LGPL this information must be displayed even if you modify
 * the source code of VRL. Neither the VRL Canvas attribution icon nor any
 * copyright statement/attribution may be removed.
 *
 * Attribution Requirements:
 *
 * If you create derived work you must do three things regarding copyright
 * notice and author attribution.
 *
 * First, the following text must be displayed on the Canvas or an equivalent location:
 * "based on VRL source code".
 * 
 * Second, the copyright notice must remain. It must be reproduced in any
 * program that uses VRL.
 *
 * Third, add an additional notice, stating that you modified VRL. In addition
 * you must cite the publications listed below. A suitable notice might read
 * "VRL source code modified by YourName 2012".
 * 
 * Note, that these requirements are in full accordance with the LGPL v3
 * (see 7. Additional Terms, b).
 *
 * Publications:
 *
 * M. Hoffer, C.Poliwoda, G.Wittum. Visual Reflection Library -
 * A Framework for Declarative GUI Programming on the Java Platform.
 * Computing and Visualization in Science, in press.
 */
package vrl.lang.model;

/**
 *
 * @author Michael Hoffer <info@michaelhoffer.de>
 */
public interface VisualCodeBuilder {

    CompilationUnitDeclaration declareCompilationUnit(String name, String packageName);
    
    BinaryOperatorInvocation assign(Scope scope, String varName, IArgument arg);

    BinaryOperatorInvocation assignConstant(Scope scope, String varName, Object constant);

    BinaryOperatorInvocation assignVariable(Scope scope, String varNameDest, String varNameSrc);

    Invocation createInstance(Scope scope, IType type, IArgument... args);

    DeclarationInvocation declareVariable(Scope scope, IType type, String varName);

    ForDeclaration invokeForLoop(ControlFlowScope scope, String varName, int from, int to, int inc);

    ClassDeclaration declareClass(CompilationUnitDeclaration scope, IType type, IModifiers modifiers, IExtends extendz, IExtends implementz);

    MethodDeclaration declareMethod(ClassDeclaration scope, IModifiers modifiers, IType returnType, String methodName, IParameters params);

    WhileDeclaration invokeWhileLoop(ControlFlowScope scope, IArgument check);

    BreakInvocation invokeBreak(ControlFlowScope scope);

    ContinueInvocation invokeContinue(ControlFlowScope scope);

    Invocation invokeMethod(ControlFlowScope scope, String varName, String mName, IType returnType, boolean isVoid, IArgument... args);

    Invocation invokeStaticMethod(ControlFlowScope scope, IType type, String mName, IType returnType, boolean isVoid, IArgument... args);

    Invocation invokeMethod(ControlFlowScope scope, String varName, MethodDeclaration mDec, IArgument... args);

    ReturnStatementInvocation returnValue(ControlFlowScope scope, IArgument arg);

    BinaryOperatorInvocation assignInvocationResult(Scope scope, String varName, Invocation invocation);

    BinaryOperatorInvocation invokeOperator(Scope scope, IArgument leftArg, IArgument rightArg, Operator operator);

    NotInvocation invokeNot(ControlFlowScope scope, IArgument arg);

    IfDeclaration invokeIf(ControlFlowScope scope, IArgument check);

    ElseIfDeclaration invokeElseIf(ControlFlowScope scope, IArgument check);
}
