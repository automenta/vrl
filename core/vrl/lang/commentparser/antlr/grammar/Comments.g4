 // -----------------------------------------------------------------------------
 // Grammar for parsing c-like comments and strings.  
 // 
 // # Suported Comment Types:
 //
 // - line comment:           //  ...
 // - multiline comment:      /*  ...  */
 // - javadoc comment:        /** ...  */
 //
 // # Supported String Types:
 // 
 // - double quotes:          "   ...   "
 // - single quotes:          '   ...   '
 // 
 // -----------------------------------------------------------------------------
 //
 // Copyright 2014 Michael Hoffer <info@michaelhoffer.de>. All rights reserved.
 //
 // Redistribution and use in source and binary forms, with or without modification, are
 // permitted provided that the following conditions are met:
 //
 //    1. Redistributions of source code must retain the above copyright notice, this list of
 //       conditions and the following disclaimer.
 //
 //    2. Redistributions in binary form must reproduce the above copyright notice, this list
 //       of conditions and the following disclaimer in the documentation and/or other materials
 //       provided with the distribution.
 // 
 // Please cite the following publication(s):
 //
 // M. Hoffer, C.Poliwoda, G.Wittum. Visual Reflection Library -
 // A Framework for Declarative GUI Programming on the Java Platform.
 // Computing and Visualization in Science, 2011, in press.
 //
 // THIS SOFTWARE IS PROVIDED BY Michael Hoffer <info@michaelhoffer.de> "AS IS" AND ANY EXPRESS OR IMPLIED
 // WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
 // FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL Michael Hoffer <info@michaelhoffer.de> OR
 // CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 // CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 // SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
 // ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 // NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
 // ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 //
 // The views and conclusions contained in the software and documentation are those of the
 // authors and should not be interpreted as representing official policies, either expressed
 // or implied, of Michael Hoffer <info@michaelhoffer.de>.
 // 

grammar Comments;	

program: (comment | string | unknowns)*;

comment: multiLineComment | lineComment;
multiLineComment: plainMultiLineComment | javadocComment | vrlMultiLineComment;
plainMultiLineComment: MULTILINE_COMMENT;
javadocComment: JAVADOC_COMMENT;
vrlMultiLineComment: VRL_MULTILINE_COMMENT;
vrlLineComment: VRL_LINE_COMMENT;
lineComment: plainLineComment|vrlLineComment;
plainLineComment: LINE_COMMENT;

string: stringDoubleQuotes | stringSingleQuote;
stringDoubleQuotes : STRING_DOUBLE;
stringSingleQuote : STRING_SINGLE;


unknowns : UNKNOWN+ ; 

// see here: http://stackoverflow.com/questions/16045209/antlr-how-to-escape-quote-symbol-in-quoted-string
// additionally we disabled greedy by using *? instead of *
STRING_DOUBLE
    :   '"' (~('"' | '\\' | '\r' | '\n') | '\\' ('"' | '\\'))*? '"'
    ;

STRING_SINGLE
    :   '\'' (~('\'' | '\\' | '\r' | '\n') | '\\' ('\'' | '\\'))*? '\''
    ;

JAVADOC_COMMENT
    :   '/**' .*? '*/'
    ;

VRL_MULTILINE_COMMENT
    :   '/*<!VRL!>' .*? '*/'
    ;

VRL_LINE_COMMENT
    :   '//<!VRL!>' ~[\r\n]*
    ;

MULTILINE_COMMENT
    :   '/*' .*? '*/'
    ;

LINE_COMMENT
    :   '//' ~[\r\n]*
    ;

// all other characters
UNKNOWN  : . ; 