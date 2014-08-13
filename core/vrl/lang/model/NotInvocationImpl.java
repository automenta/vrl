/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package vrl.lang.model;

/**
 *
 * @author Michael Hoffer &lt;info@michaelhoffer.de&gt;
 */
public class NotInvocationImpl extends InvocationImpl implements NotInvocation {

        public NotInvocationImpl(Scope parent, IArgument arg) {

        super(parent, "", null, "not", Type.BOOLEAN, false, false, true, arg);
    }
    
    @Override
    public IArgument getArgument() {
        return getArguments().get(0);
    }
}
