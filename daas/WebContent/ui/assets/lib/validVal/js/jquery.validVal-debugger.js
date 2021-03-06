﻿/*	
 *	jQuery validVal debugger version 1.0.1
 *	Helps debugging the validation of forms
 *
 *	Copyright (c) 2013 Fred Heusschen
 *	www.frebsite.nl
 *
 *	Dual licensed under the MIT and GPL licenses.
 *	http://en.wikipedia.org/wiki/MIT_License
 *	http://en.wikipedia.org/wiki/GNU_General_Public_License
 */


(function( $ )
{

	if ( !console )				console 			= {};
	if ( !console.log ) 		console.log 		= function() {};
	if ( !console.info ) 		console.info 		= function() {};
	if ( !console.warn ) 		console.warn 		= function() {};
	if ( !console.error ) 		console.error 		= function() {};
	if ( !console.group ) 		console.group 		= function() {};
	if ( !console.groupEnd )	console.groupEnd	= function() {};

    $.fn.validValDebug = function( addBtn )
    {

		//	validVal not found, don't proceed
	    if ( !$.fn.validVal )
	    {
			console.error( 'validVal-plugin not found!' );
			return this;
	    }

		//	Old version of validVal, don't proceed
	    if ( 4.4 > $.fn.validVal.version[ 0 ] + ( $.fn.validVal.version[ 1 ] / 10 ) )
	    {
	    	console.error( 'validValDebug needs at least version 4.4.0 of the validVal-plugin.' );
		    return this;
	    }

		//	Fired on multiple forms
	    if ( this.length > 1 )
        {
            return this.each(
            	function()
            	{
                	$(this).validValDebug( addBtn );
                }
            );
        }

        var form = this;

		//	Form is not validVal, don't proceed
		if ( !form.data( 'vv-isValidVal' ) )
		{
			console.log( nodeStringFromNode( form[ 0 ] ) + ' is not a validVal form.' );
			return form;
		}

		//	Add the validate button
		if ( typeof addBtn != 'boolean' )
		{
			addBtn = true;
		}
		if ( addBtn )
		{
			var panel = $( '<div />' )
				.appendTo( form )
				.css({
					'padding': '20px 0'
				});

			$( '<input type="submit" value="Validate" />' )
				.appendTo( panel )
				.bind(
					'click.vv',
					function( event )
					{
						event.preventDefault();
						event.stopPropagation();
						form.triggerHandler( 'validate.vv', [ form, false ] );
					}
				);
		}

		//	Get the options
		var opts = form.triggerHandler( 'options.vv' );

		//	Bind the debugging info to the fields
		$('input, select, textarea', form).bind(
			'isValid.vv',
			function( e, valid, callCallback, invalidCheck )
			{

				if ( valid )
				{
					console.group( 'Valid:\n', this );
					console.log( 'Validation(s) that passed: "' + $(this).data( 'vv-validations' ).split( ' ' ).join( '", "' ) + '".' );
				}
				else
				{
					console.group( 'NOT VALID!\n', this );
					var msg = 'Validation "' + invalidCheck + '" failed for the value "' + $(this).val() + '"';
					if ( opts.validations[ invalidCheck ] )
					{
						msg += ':\n';
						msg += ( '\t' + opts.validations[ invalidCheck ] )
							.split( '\r\n' ).join( '\r' )
							.split( '\n\r' ).join( '\r' )
							.split( '\r\r' ).join( '\r' )
							.split( '\r' ).join( '\n' )
							.split( '\t' ).join( '    ' );
					}
					else
					{
						msg += '.';
					}
					console.warn( msg + '\n\n' );
				}
				console.groupEnd();
			}
		);
		
		return form;
    }

	function nodeStringFromNode( node )
	{
		var str = node.nodeName.toLowerCase();
		if ( node.id )
		{
			str += '#' + node.id;
		}
		else if ( node.name )
		{
			str += '[name="' + node.name + '"]';
		}

		return str;
	}


})( jQuery );