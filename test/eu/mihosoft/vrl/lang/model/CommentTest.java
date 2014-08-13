/* 
 * CommentTest.java
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

package eu.mihosoft.vrl.lang.model;

import vrl.lang.model.Comment;
import vrl.base.IOUtil;
import vrl.lang.VCommentParser;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.Assert;
import org.junit.Test;

/**
 *
 * @author Michael Hoffer &lt;info@michaelhoffer.de&gt;
 */
public class CommentTest {

    @Test
    public void testRealCode02VComment() {

        boolean success = false;

        List<Comment> comments = new ArrayList<>();

        try {
            comments = VCommentParser.parse(getResourceAsStringReader("RealCode02.txt"));
            success = true;
        } catch (IOException ex) {
            Logger.getLogger(CommentTest.class.getName()).log(Level.SEVERE, null, ex);
        }

        Assert.assertTrue("Parser must not throw an exception", success);

//        System.out.println("#comments: " + comments.size());
        Assert.assertTrue("Expected 34 comments, got" + comments.size(), comments.size() == 34);
    }

    public static InputStream getResourceAsStream(String resourceName) {
        return CommentTest.class.getResourceAsStream("/eu/mihosoft/vrl/lang/" + resourceName);
    }

    public static Reader getResourceAsStringReader(String resourceName) {

        InputStream is = CommentTest.class.getResourceAsStream("/eu/mihosoft/vrl/lang/" + resourceName);
        
//        BufferedReader bR = null;
//        try {
//            bR = new BufferedReader(new InputStreamReader(is, "UTF-8"));
//            bR.mark(8192 /*defaultCharBufferSize*/);
//        } catch (UnsupportedEncodingException ex) {
//            Logger.getLogger(CommentTest.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (IOException ex) {
//            Logger.getLogger(CommentTest.class.getName()).log(Level.SEVERE, null, ex);
//        }


        String tmpCode = IOUtil.convertStreamToString(is);

        return new StringReader(tmpCode);

    }

}
