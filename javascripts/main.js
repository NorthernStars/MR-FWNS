// Set prototype functions
String.prototype.contains = function(it) { return this.indexOf(it) != -1; };
String.prototype.trim = function() { return this.replace(/^\s+|\s+$/g, ''); };
String.prototype.startsWith = function(it) { return this.trim().indexOf(it) == 0; }
String.prototype.startsWithArrayItem = function( array) {
	for( var i=0; i<array.length; i++ ){
		if( this.startsWith(array[i]) ){
			return true;
		}
	}
	return false;
};
String.prototype.replaceAll = function( token, newToken, ignoreCase ) {
    var _token;
    var str = this + "";
    var i = -1;

    if ( typeof token === "string" ) {

        if ( ignoreCase ) {

            _token = token.toLowerCase();

            while( (
                i = str.toLowerCase().indexOf(
                    token, i >= 0 ? i + newToken.length : 0
                ) ) !== -1
            ) {
                str = str.substring( 0, i ) +
                    newToken +
                    str.substring( i + token.length );
            }

        } else {
            return this.split( token ).join( newToken );
        }

    }
    return str;
};

// arrays
var javaKeywords = [ 'class', 'public', 'private', 'protected', 'static', 'return', 'null', 'extends', 'package',
                    'implements', 'final', 'synchronized', 'interface', 'abstract', 'do', 'if', 'else', 'new',
                    'for', 'while', 'switch', 'case' ];
var javaCommentSingle = '//';
var javaCommentMultiOpen = '/*';
var javaCommentMultiClose = '*/';
var javaAnnotations = [ '@' ];

function checkJavaSyntax(){
	var elements = document.getElementsByTagName('pre');
	for( var i=0; i<elements.length; i++ ){
		
		var element = elements[i];	
		var lines = elements[i].innerHTML.split('\n');
		
		// highlight keywords
		var comment = false;
		javaKeywords.forEach( function(keyword){			
			for( var y=0; y<lines.length; y++ ){
				var line = lines[y];
				if( line.startsWith(javaCommentMultiOpen) ) comment = true;
				
				if( !comment && !line.startsWithArrayItem(javaCommentSingle) && !line.startsWithArrayItem(javaAnnotations) ){
					lines[y] = line.replace(keyword+' ', '<span class="java-keyword">'+keyword+' </span>');
				}
				
				if( line.startsWith(javaCommentMultiClose) ) comment = false;
			}
		} );
		
		// highlight comments
		comment = false;
		for( var y=0; y<lines.length; y++ ){
			var line = lines[y];
			if( line.startsWith(javaCommentMultiOpen) ) comment = true;
			
			if( comment || line.startsWithArrayItem(javaCommentSingle) ){
				lines[y] = line.replace(line, '<span class="java-comment">'+line+'</span>');
			}
			
			if( line.startsWith(javaCommentMultiClose) ) comment = false;
		}
		
		// highlight annotation
		for( var y=0; y<lines.length; y++ ){
			var line = lines[y];
			if( line.startsWith(javaCommentMultiOpen) ) comment = true;
			
			if( !comment && !line.startsWithArrayItem(javaCommentSingle) && line.startsWithArrayItem(javaAnnotations) ){
				lines[y] = line.replace(line, '<span class="java-annotation">'+line+'</span>');
			}
			
			if( line.startsWith(javaCommentMultiClose) ) comment = false;
		}
		
		element.innerHTML = lines.join('\n');
		
	}
}