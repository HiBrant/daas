﻿/*	
 *	jQuery validVal version 4.4.2
 *	demo's and documentation:
 *	validval.frebsite.nl
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

	//	validVal already excists
    if ( $.fn.validVal )
    {
        return;
    }

	//	create debugger is it doesn't already excists
	if ( !$.fn.validValDebug )
	{
		$.fn.validValDebug = function( b )
		{
			$.fn.validVal.debug( 'validVal debugger not installed!' );
			return this;
		}
	}


	var inputSelector = 'textarea, select, input:not( [type="button"], [type="submit"], [type="reset"] )';

    $.fn.validVal = function( o, c, debug )
    {
        if ( this.length > 1 )
        {
            return this.each(
            	function()
            	{
                	$(this).validVal( o, c, debug );
                }
            );
        }

        //	debug option can be any of the arguments
        if ( typeof o == 'boolean' )
        {
	        debug = o;
	        o = null;
        }
        else if ( typeof c == 'boolean' )
        {
	        debug = c;
	        c = null;
        }

        var form = this,
            opts = $.extend( true, {}, $.fn.validVal.defaults, o ),
            clss = $.extend( true, {}, $.fn.validVal.classes, c );



        //	DEPRECATED
        if ( typeof opts.invalidFormFunc == 'function' )
        {
            $.fn.validVal.deprecated( 'callback function "invalidFormFunc"', '"form.onInvalid"' );
            opts.form.onInvalid = opts.invalidFormFunc;
        }
        if ( typeof opts.onSubmit == 'function' )
        {
            $.fn.validVal.deprecated( 'callback function "onSubmit"', '"form.onValid"' );
            opts.form.onValid = opts.onSubmit;
        }
        if ( typeof opts.onReset == 'function' )
        {
            $.fn.validVal.deprecated( 'callback function "onReset"', '"form.onReset"' );
            opts.form.onReset = opts.onReset;
        }
        if ( typeof opts.validFieldFunc == 'function' )
        {
            $.fn.validVal.deprecated( 'callback function "validFieldFunc"', '"fields.onValid"' );
            opts.fields.onValid = opts.validFieldFunc;
        }
        if ( typeof opts.invalidFieldFunc == 'function' )
        {
            $.fn.validVal.deprecated( 'callback function "invalidFieldFunc"', '"fields.onInvalid"' );
            opts.fields.onInvalid = opts.invalidFieldFunc;
        }
        if ( typeof opts.validate.hiddenFields == 'boolean' )
        {
            $.fn.validVal.deprecated( 'option "validate.hiddenFields"', '"validate.fields.hidden"' );
            opts.validate.fields.hidden = opts.validate.hiddenFields;
        }
        if ( typeof opts.validate.disabledFields == 'boolean' )
        {
            $.fn.validVal.deprecated( 'option "validate.disabledFields"', '"validate.fields.disabled"' );
            opts.validate.fields.disabled = opts.validate.disabledFields;
        }
        //	/DEPRECATED



		//	destroy if re-created
		if ( data( form, 'isValidVal' ) )
		{
			form.trigger( 'destroy.vv' );
        }
		form.data( 'vv-isValidVal', true );

        //	collect validations
        opts.validations = {};
        if ( $.fn.validVal.customValidations )
        {
            opts.validations = $.extend( opts.validations, $.fn.validVal.customValidations );
        }
        if ( opts.customValidations )
        {
            opts.validations = $.extend( opts.validations, opts.customValidations );
        }
        opts.validations = $.extend( opts.validations, $.fn.validVal.defaultValidations );

        //	bind events
        form.bind(
            'addField.vv',
            function( event, el )
            {
                event.stopPropagation();

                var $ff = $( el );

                if ( data( $ff, 'isValidVal' ) )
                {
                    $ff.trigger( 'destroy.vv' );
                }
                $ff.data( 'vv-isValidVal', true );

                get_validations( $ff, opts );

                //	bind events
                $ff.bind(
                    'focus.vv',
                    function( event )
                    {
                        clear_placeholdervalue( $ff );
                        $ff.addClass( getclass( 'focus' ) );
                    }
                );
                $ff.bind(
                    'blur.vv',
                    function( event )
                    {
                        $ff.removeClass( getclass( 'focus' ) );
                        $ff.trigger( 'validate.vv', [ opts.validate.onBlur ] );
                    }
                );
                $ff.bind(
                    'keyup.vv',
                    function( event )
                    {
                        if ( !preventkeyup( event.keyCode ) )
                        {
                        	$ff.trigger( 'validate.vv', [ opts.validate.onKeyup, false ] );
                        }
                    }
                );
                $ff.bind(
                    'validate.vv',
                    function( event, onEvent, fixPlaceholder )
                    {
                        event.stopPropagation();

                        if ( onEvent === false )
                        {
                            return;
                        }
						if ( typeof fixPlaceholder != 'boolean' )
						{
							fixPlaceholder = true;
						}

                        $ff.data( 'vv-isValid', 'valid' );

                        if ( ( $ff.is( ':hidden' ) && !opts.validate.fields.hidden ) ||
                            ( $ff.is( ':disabled' ) && !opts.validate.fields.disabled )
                        ) {
                            return;
                        }

						if ( fixPlaceholder )
						{
							clear_placeholdervalue( $ff );
                        }
						if ( typeof opts.fields.onValidate == 'function' )
						{
                            opts.fields.onValidate.call( $ff[0], form, opts.language );
                        }

                        var invalid_check = false,
                            val = trim( $ff.val() );

                        for ( var v in opts.validations )
                        {
                            var f = opts.validations[ v ];
                            if ( has_validation( $ff, v ) && typeof f == 'function' )
                            {
                                if ( !f.call( $ff[0], val ) )
                                {
                                    invalid_check = v;
                                    break;
                                }
                            }
                        }

                        var is_valid = ( invalid_check ) ? false : true;
                        var callCallback = ( is_valid )
                        	? ( onEvent !== 'invalid' )
                        	: ( onEvent !== 'valid' );

                        $ff.trigger( 'isValid.vv', [ is_valid, callCallback, invalid_check ] );
                        set_validationgroup( $ff, is_valid );

						if ( fixPlaceholder )
						{
							restore_placeholdervalue( $ff );
						}
						if ( debug && invalid_check )
						{
	                        $.fn.validVal.debug( 'invalid validation: ' + invalid_check );
	                    }
                        return is_valid;
                    }
                );
                $ff.bind(
                	'isValid.vv',
                	function( event, valid, callCallback )
                	{
	                	event.stopPropagation();

	                	if ( typeof valid == 'boolean' )
	                	{
		                	if ( valid )
		                	{
			                	$ff.data( 'vv-isValid', 'valid' );
			                	if ( callCallback )
			                	{
			                		set_valid( $ff, form, opts );
			                	}
		                	}
		                	else
		                	{
			                	$ff.data( 'vv-isValid', 'NOT' );
			                	if ( callCallback )
			                	{
				                	set_invalid( $ff, form, opts );
				                }
		                	}
	                	}
	                	else
	                	{
		                	return data( $ff, 'isValid' ) == 'valid';
	                	}
                	}
                );
                $ff.bind(
                	'validations.vv',
                	function( event, validations )
                	{
	                	event.stopPropagation();

	                	if ( typeof validations == 'undefined' )
	                	{
		                	return data( $ff, 'validations' ).split( ' ' );
	                	}
	                	if ( validations instanceof Array )
	                	{
		                	validations = validations.join( ' ' );
	                	}
	                	if ( typeof validations == 'string' )
	                	{
		                	$ff.data( 'vv-validations', validations );
	                	}
                	}
                );
                $ff.bind(
                    'addValidation.vv',
                    function( event, validation )
                    {
                        event.stopPropagation();
                        
                        var validations = data( $ff, 'validations' ).split( ' ' );
                        validations.push( validation );
                        $ff.data( 'vv-validations', validations.join( ' ' ) );
                    }
                );
                $ff.bind(
                    'removeValidation.vv',
                    function( event, validation )
                    {
                        event.stopPropagation();
                        
                        var validations = ' ' + data( $ff, 'validations' ) + ' ';
                        validations = validations.split( ' ' + validation + ' ' );
                        $ff.data( 'vv-validations', validations.join( ' ' ) );
                    }
                );
                $ff.bind(
                    'destroy.vv',
                    function( event )
                    {
                        event.stopPropagation();
                        
                        $ff.unbind( '.vv' );
                        $ff.data( 'vv-isValidVal', false );
                    }
                );

                //	placeholder
                if ( is_placeholderfield( $ff ) )
                {
                    if ( $ff.val() == '' )
                    {
                        $ff.val( data( $ff, 'placeholder-value' ) );
                    }
                    if ( has_validation( $ff, 'passwordplaceholder' ) )
					{
						if ( has_placeholdervalue( $ff ) ) try
						{
							$ff[ 0 ].type = 'text';
						}
						catch( err ) {};
					}
                    if ( has_placeholdervalue( $ff ) )
                    {
                        $ff.addClass( getclass( 'inactive' ) );
                    }
                    if ( $ff.is( 'select' ) )
                    {
                        $ff.find( 'option:eq(' + data( $ff, 'placeholder-number' ) + ')' ).addClass( getclass( 'inactive' ) );
                        $ff.bind(
                            'change.vv',
                            function( event )
                            {
                                if ( has_placeholdervalue( $ff ) )
                                {
                                    $ff.addClass( getclass( 'inactive' ) );
                                }
                                else
                                {
                                    $ff.removeClass( getclass( 'inactive' ) );
                                }
                            }
                        );
                    }
                }

                //	corresponding
                if ( is_correspondingfield( $ff ) )
                {
                    $(inputSelector).filter('[name="' + $ff.data( 'vv-corresponding' ) + '"]').bind(
                        'blur.vv',
                        function( event )
                        {
                            $ff.trigger( 'validate.vv', [ opts.validate.onBlur ] );
                        }
                    ).bind(
                    	'keyup.vv',
	                    function( event )
	                    {
	                        if ( !preventkeyup( event.keyCode ) )
	                        {
	                        	$ff.trigger( 'validate.vv', [ opts.validate.onKeyup, false ] );
	                        }
	                    }
                    );
                }

                //	autotabbing
                if ( has_validation( $ff, 'autotab' ) )
                {
                    var max = $ff.attr( 'maxlength' ),
                    	tab = $ff.attr( 'tabindex' ),
                    	$next = $(inputSelector).filter('[tabindex="' + ( parseInt( tab ) + 1 ) + '"]');

                    if ( $ff.is( 'select' ) )
                    {
                        if ( tab )
                        {
                            $ff.bind(
                                'change.vv',
                                function( event )
                                {
                                    if ( $next.length )
                                    {
                                        $next.focus();
                                    }
                                }
                            );
                        }
                    }
                    else
                    {
                        if ( max && tab )
                        {
                            $ff.bind(
                                'keyup.vv',
                                function( event )
                                {
                                    if ( $ff.val().length == max )
                                    {
                                        if ( !preventkeyup( event.keyCode ) )
                                        {
	                                        $ff.trigger( 'blur' );
                                        	if ( $next.length )
                                        	{
                                            	$next.focus();
                                            }
                                        }
                                    }
                                }
                            );
                        }
                    }
                }

                //	autofocus
                if ( has_validation( $ff, 'autofocus' ) && !$ff.is( ':disabled' ) )
                {
                    $ff.focus();
                }
            }
        );

        opts.validate.fields.filter( $(inputSelector, form) ).each(
        	function()
        	{
            	form.trigger( 'addField.vv', [ $(this) ] );

            }
        ).filter( 'select, input[type="checkbox"], input[type="radio"]' ).bind(
            'change.vv',
            function( event )
            {
                $(this).trigger( 'blur.vv' );
            }
        );

        form.bind(
			'destroy.vv',
			function( event )
			{
				event.stopPropagation();

				form.unbind( '.vv' );
				opts.validate.fields.filter( $(inputSelector, form) ).trigger( 'destroy.vv' );
				form.data( 'vv-isValidVal', false );
			}
		);

		form.bind(
			'validate.vv',
			function( event, body, callCallback )
			{
				event.stopPropagation();

                if ( typeof body == 'undefined' )
                {
                    body = form;
                    callCallback = true;
                }
                else if ( typeof callCallback != 'boolean' )
                {
                    callCallback = false;
                }

                if ( typeof opts.form.onValidate == 'function' )
                {
                    opts.form.onValidate.call( form[0], opts.language );
                }

                var miss_arr = $(),
                    data_obj = {};

                opts.validate.fields.filter( $(inputSelector, body) ).each(
                	function()
                	{
	                    var $ff = $(this);
	                    if ( data( $ff, 'isValidVal' ) )
	                    {
	
	                        $ff.trigger( 'validate.vv', [ opts.validate.onSubmit ] );
	                        var v = $ff.val();
	
	                        if ( data( $ff, 'isValid' ) == 'valid' )
	                        {
	                            if ( $ff.is( '[type="radio"]' ) || $ff.is( '[type="checkbox"]' ) )
	                            {
	                                if ( !$ff.is( ':checked' ) )
	                                {
	                                    v = '';
	                                }
	                            }
	                            if (typeof v == 'undefined' || v == null)
	                            {
	                                v = '';
	                            }
	                            if ( v.length > 0 )
	                            {
	                                data_obj[ $ff.attr( 'name' ) ] = v;
	                            }
	
	                        }
	                        else
	                        {
	                            if ( opts.validate.onSubmit !== false )
	                            {
	                                miss_arr = miss_arr.add( $ff );
	                            }
	                        }
	                    }
	                }
	            );

                if ( miss_arr.length > 0 )
                {
                    if ( typeof opts.form.onInvalid == 'function' && callCallback )
                    {
                        opts.form.onInvalid.call( form[0], miss_arr, opts.language );
                    }
                    return false;

                }
                else
                {
                    if ( typeof opts.form.onValid == 'function' && callCallback )
                    {
                        opts.form.onValid.call( form[0], opts.language );
                    }
                    return data_obj;
                }
            }
        );

        form.bind(
            'submitForm.vv',
            function( event )
            {
                event.stopPropagation();

                var result = form.triggerHandler( 'validate.vv' );
                if ( result )
                {
                    opts.validate.fields.filter( $(inputSelector, form) ).each(
                    	function()
                    	{
                        	clear_placeholdervalue( $(this) );
                        }
                    );
                }
                return result;
            }
        );

        form.bind(
            'resetForm.vv',
            function( event )
            {
                event.stopPropagation();

                if ( typeof opts.form.onReset == 'function' )
                {
                    opts.form.onReset.call( form[0], opts.language );
                }

                opts.validate.fields.filter( $(inputSelector, form) ).each(
                	function()
                	{
		                var $ff = $(this);
	                    if ( is_placeholderfield( $ff ) )
	                    {
	                        $ff.addClass( getclass( 'inactive' ) );
	                        $ff.val( data( $ff, 'placeholder-value' ) );
	                    }
	                    else
	                    {
	                        $ff.val( data( $ff, 'original-value' ) );
	                    }
	                    $ff.trigger( 'isValid.vv', [ true, true ] );
	                }
	            );
                return false;
            }
        );

		form.bind(
			'options.vv',
			function( event, options )
			{
                event.stopPropagation();
				
				if ( typeof options == 'object' )
				{
					opts = $.extend( opts, options );
				}
				return opts;
			}
		);


        // bind to native submit/reset
        if ( form.is( 'form' ) )
        {
            form.attr( 'novalidate', 'novalidate' );
            form.bind(
                'submit.vv',
                function( event, validate )
                {
                    if ( typeof validate != 'boolean' )
                    {
                        validate = true;
                    }
                    return ( validate )
                        ? form.triggerHandler( 'submitForm.vv' )
                        : true;
                }
            );
            form.bind(
                'reset.vv',
                function( event )
                {
                    return form.triggerHandler( 'resetForm.vv' );
                }
            );
        }

        return form;
    };

	$.fn.validVal.version = [ 4, 4, 2 ];

    $.fn.validVal.defaults = {
        'selectPlaceholder'	: 0,
        'supportHtml5'		: true,
        'language'			: 'en',
        'customValidations'	: {},
        'validate'	: {
            'onBlur'	: true,
            'onSubmit'	: true,
            'onKeyup'	: false,
            'fields'	: {
                'hidden'	: false,
                'disabled'	: false,
                'filter'	: function( $i )
                {
                    return $i;
                }
            }
        },
        'fields'	: {
            'onValidate'	: null,
            'onValid'		: function()
            {
                var $f = $(this);
                $f.add( $f.parent() ).removeClass( getclass( 'invalid' ) );
            },
            'onInvalid'		: function()
            {
                var $f = $(this);
                $f.add( $f.parent() ).addClass( getclass( 'invalid' ) );
            }
        },
        'form'	: {
            'onReset'	: null,
            'onValidate': null,
            'onValid'	: null,
            'onInvalid'	: function( fieldArr, language )
            {
                switch ( language )
                {
                    case 'nl':
                        msg = 'Let op, niet alle velden zijn correct ingevuld.';
                        break;

                    case 'de':
                        msg = 'Achtung, nicht alle Felder sind korrekt ausgefuellt.';
                        break;

                    case 'es':
                        msg = 'Atención, no se han completado todos los campos correctamente.';
                        break;

                    case 'en':
                    default:
                        msg = 'Attention, not all fields have been filled out correctly.';
                        break;
                }
                alert( msg );
                fieldArr.first().focus();
            }
        },
        'keepClasses'	: [ 'required' ],
        'keepAttributes': [ 'pattern' ]
    };

    $.fn.validVal.defaultValidations = {
        'required': function( v )
        {
            var $f = $(this);

            if ( $f.is( '[type="radio"]' ) || $f.is( '[type="checkbox"]' ) )
            {
                if ( $f.is( '[type="radio"]' ) )
                {
                    var name = $f.attr( 'name' );
                    if ( name && name.length > 0 )
                    {
                        $f = $( 'input[name="' + name + '"]' );
                    }
                }
                if ( !$f.is( ':checked' ) )
                {
                    return false;
                }

            }
            else if ( $f.is( 'select' ) )
            {
                if ( is_placeholderfield( $f ) )
                {
                    if ( has_placeholdervalue( $f ) )
                    {
                        return false;
                    }
                }
                else
                {
                    if ( v.length == 0 )
                    {
                        return false;
                    }
                }

            }
            else
            {
                if ( v.length == 0 )
                {
                    return false;
                }
            }
            return true;
        },
        'Required': function( v )
        {
            return $.fn.validVal.defaultValidations.required.call( this, v );
        },
        'requiredgroup': function( v )
        {
	        var $f = $(this),
	        	gr = data( $f, 'requiredgroup' );

	        if ( gr.length )
	        {
                $f = $(inputSelector).filter( ':vv-requiredgroup(' + gr + ')' );
            }
            var result = false;
            $f.each(
            	function()
            	{
	            	var f = this;
		            if ( $.fn.validVal.defaultValidations.required.call( f, trim( $(f).val() ) ) )
		            {
			            result = true;
		            }
		        }
            );
            return result;
        },
        'corresponding': function( v )
        {
			var $f = $(inputSelector).filter('[name="' + data( $(this), 'corresponding' ) + '"]');
			clear_placeholdervalue( $f );
			var org = trim( $f.val() );
			restore_placeholdervalue( $f );
            return ( v == org );
        },
        'number': function( v )
        {
            v = strip_whitespace( v );
            if ( v.length == 0 )
            {
                return true;
            }
            if ( isNaN( v ) )
            {
                return false;
            }
            return true;
        },
        'email': function( v )
        {
            if ( v.length == 0 )
            {
                return true;
            }
            var r = /^([a-zA-Z0-9_\.\-+])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4})+$/;

            return r.test( v );
        },
        'url': function( v )
        {
            if ( v.length == 0 )
            {
                return true;
            }
            if ( v.match(/^www\./) )
            {
                v = "http://" + v;
            }
            return v.match(/^(http\:\/\/|https\:\/\/)(.{4,})$/);
        },
        'pattern': function( v )
        {
            if ( v.length == 0 )
            {
                return true;
            }
            var $f = $(this),
                p = data( $f, 'pattern' );

            if ( p.substr( 0, 1 ) == '/' )
            {
                p = p.substr( 1 );
            }
            if ( p.substr( p.length - 1 ) == '/' )
            {
                p = p.substr( 0, p.length -1 );
            }
            return new RegExp( p ).test( v );
        }
    };

    $.fn.validVal.classes = {
        //	key		:  class
        'focus'		: 'focus',
        'invalid'	: 'invalid',
        'inactive'	: 'inactive'
    };

    $.fn.validVal.debug = function( msg )
    {
        if ( typeof console != 'undefined' )
        {
	        if ( typeof console.log != 'undefined' )
	        {
	            console.log( 'validVal: ' + msg );
	        }
        }
    };

    $.fn.validVal.deprecated = function( func, alt )
    {
        if ( typeof console != 'undefined' )
        {
        	if ( typeof console.error != 'undefined' )
        	{
	        	console.error( func + ' is DEPRECATED, use ' + alt + ' instead.' );
	        }
        }
    };


    //	test for borwser support
    $.fn.validVal.support = {
	    touch: (function()
	    {
		    return 'ontouchstart' in document.documentElement;
	    })(),

	    placeholder: (function()
	    {
		    return 'placeholder' in document.createElement( 'input' );
	    })()
    };


    //	extend jQuery selector engine
    $.expr[':']['vv-requiredgroup'] = function( elem, counter, params )
    {
       return selector_engine( 'requiredgroup', elem, counter, params );
    };
    $.expr[':']['vv-validationgroup'] = function( elem, counter, params )
    {
       return selector_engine( 'validationgroup', elem, counter, params );
    };
    function selector_engine( string, elem, counter, params )
    {
	    if ( !elem || !params || !params[ 3 ] )
	    {
            return false;
        }
        var group = data( $(elem), string );
        if ( !group.length )
        {
            return false;
        }
        return group == params[ 3 ];
    }

    //	placeholder functions
    function is_placeholderfield( $f )
    {
        return has_validation( $f, 'placeholder' );
    }
    function has_placeholdervalue( $f )
    {
        if ( trim( $f.val() ) == data( $f, 'placeholder-value' ) )
        {
            return true;
        }
        return false;
    }
    function clear_placeholdervalue( $f )
    {
        if ( is_placeholderfield( $f ) )
        {
            if ( has_placeholdervalue( $f ) && !$f.is( 'select' )  )
            {
                $f.val( '' );
                $f.removeClass( getclass( 'inactive' ) );
                
				if ( has_validation( $f, 'passwordplaceholder' ) ) try
                {
	                $f[ 0 ].type = 'password';
                }
                catch( err ) {};
            }
        }
    }
    function restore_placeholdervalue( $f )
    {
        if ( is_placeholderfield( $f ) )
        {
            if ( trim( $f.val() ) == '' && !$f.is( 'select' ) )
            {
                $f.val( data( $f, 'placeholder-value' ) );
                $f.addClass( getclass( 'inactive' ) );

                if ( has_validation( $f, 'passwordplaceholder' ) ) try
                {
	                $f[ 0 ].type = 'text';
                }
                catch( err ) {};
            }
        }
    }

    //	corresponding
    function is_correspondingfield( $f )
    {
        return has_validation( $f, 'corresponding' );
    }

    //	validate group
    function is_validationgroupfield( $f )
    {
	    return has_validation( $f, 'validationgroup' );
    }
    function set_validationgroup( $f, valid )
    {
	    if ( is_validationgroupfield( $f ) )
	    {
			var gr = data( $f, 'validationgroup' );
			if ( gr.length )
			{
				$(inputSelector).filter( ':vv-validationgroup(' + gr + ')' ).not( $f ).each(
					function()
					{
						$(this).trigger( 'isValid.vv', [ valid, true ] );
					}
				);
			}
        }
    }

    //	valid/invalid
    function set_valid( $f, f, o )
    {
        if ( typeof o.fields.onValid == 'function' )
        {
            o.fields.onValid.call( $f[0], f, o.language );
        }
    }
    function set_invalid( $f, f, o )
    {
        if ( typeof o.fields.onInvalid == 'function' )
        {
            o.fields.onInvalid.call( $f[0], f, o.language );
        }
    }

    //	HTML5 stuff
    function has_html5_attr( $f, a )
    {
        // non HTML5 browsers
        if ( typeof $f.attr( a ) == 'undefined' )
        {
            return false;
        }
        // HTML5 browsers
        if ( $f.attr( a ) === 'false' || $f.attr( a ) === false )
        {
            return false;
        }
        return true;
    }
    function has_html5_type( $f, t )
    {
        // cool HTML5 browsers
        if ( $f.attr( 'type' ) == t )
        {
            return true;
        }

        // non-HTML5 but still cool browsers
        if ( $f.is( 'input[type="' + t + '"]' ) )
        {
            return true;
        }

        //	non-HTML5, non-cool browser
        var res = get_outerHtml( $f );
        if ( res.indexOf( 'type="' + t + '"' ) != -1 || res.indexOf( 'type=\'' + t + '\'' ) != -1 || res.indexOf( 'type=' + t + '' ) != -1 )
        {
            return true;
        }
        return false;
    }

    //	get all validations
    function get_validations( $f, o )
    {

	    var classes = $f.attr( 'class' );

	    //	DEPRECATED
        var alt = $f.attr( 'alt' );
        if ( alt && alt.length > 0 && !$f.hasClass( 'pattern' ) )
        {
            if ( $f.hasClass( 'corresponding' ) )
            {
                $.fn.validVal.deprecated( 'name in "alt"-attribute', 'class="corresponding:name"' );
                $f.removeClass( 'corresponding' );
                $f.removeAttr( 'alt' );
                if ( o.supportHtml5 )
                {
                    $f.data( 'vv-corresponding', alt );
                }
                else
                {
                    $f.addClass( 'corresponding:' + alt );
                }
            }
            if ( $f.hasClass( 'required' ) )
            {
                $.fn.validVal.deprecated( 'grouping required elements in the "alt"-attribute', 'class="requiredgroup:name"' );
                $f.removeClass( 'required' );
                $f.removeAttr( 'alt' );
                if ( o.supportHtml5 )
                {
                    $f.data( 'vv-requiredgroup', alt );
                }
                else
                {
                    $f.addClass( 'requiredgroup:' + alt );
                }
            }
        }
        if ( classes && classes.indexOf( 'required:' ) != -1 )
        {
	        $.fn.validVal.deprecated( 'grouping required elements with class="required:name"', 'class="requiredgroup:name"' );
        }
        //	/DEPRECATED


        var validations = [],
        	original_value = get_original_value( $f, o );

        //	refactor HTML5 usage
        if ( o.supportHtml5 )
        {

            var valids = data( $f, 'validations' );
            if ( valids.length )
            {
                validations.push( valids );
            }

            //	placeholder attribute, only use if placeholder not supported by browser or placeholder not in keepAttributes-option
            if ( has_html5_attr( $f, 'placeholder' ) && $f.attr( 'placeholder' ).length > 0 )
            {
                if ( !$.fn.validVal.support.placeholder || $.inArray( 'placeholder', o.keepAttributes ) == -1 )
                {
                	$f.data( 'vv-placeholder-value', $f.attr( 'placeholder' ) );
                }
            }
            var placeh = data( $f, 'placeholder-value' );
            if ( placeh.length )
            {
	            removeAttr( $f, 'placeholder', o );
                validations.push( 'placeholder' );
            }

            //	pattern attribute
            if ( has_html5_attr( $f, 'pattern' ) && $f.attr( 'pattern' ).length > 0 )
            {
                $f.data( 'vv-pattern', $f.attr( 'pattern' ) );
                removeAttr( $f, 'pattern', o );
                validations.push( 'pattern' );
            }

            //	corresponding, required group and validation group
            var dts = [ 'corresponding', 'requiredgroup', 'validationgroup' ];
            for ( var d = 0, l = dts.length; d < l; d++ )
            {
            	if ( data( $f, dts[ d ] ).length )
            	{
	                validations.push( dts[ d ] );
	            }
            }

            // attributes
            var atr = [ 'required', 'autofocus' ];
            for ( var a = 0, l = atr.length; a < l; a++ )
            {
                if ( has_html5_attr( $f, atr[ a ] ) )
                {
                    validations.push( atr[ a ] );
                    removeAttr( $f, atr[ a ], o );
                }
            }

            //	type-values
            var typ = [ 'number', 'email', 'url' ];
            for ( var t = 0, l = typ.length; t < l; t++ )
            {
                if ( has_html5_type( $f, typ[ t ] ) )
                {
                    validations.push( typ[ t ] );
                }
            }
        }

        //	refactor non-HTML5 usage
        var classes = $f.attr( 'class' );
        if ( classes && classes.length )
        {

            //	placeholder
            if ( $f.hasClass( 'placeholder' ) )
            {
                removeClass( $f, 'placeholder', o );
                $f.data( 'vv-placeholder-value', original_value );
                validations.push( 'placeholder' );
                original_value = '';
            }

            //	corresponding
            var corsp = 'corresponding:',
                start = classes.indexOf( corsp );

            if ( start != -1 )
            {
                var corrcls = classes.substr( start ).split( ' ' )[ 0 ],
                    corresp = corrcls.substr(  corsp.length );

                if ( corresp.length )
                {
                    $f.removeClass( corrcls );
                    $f.data( 'vv-corresponding', corresp );
                    validations.push( 'corresponding' );
                }
            }

            //	pattern
            //	still using alt-attribute...
            if ( $f.hasClass( 'pattern' ) )
            {
                removeClass( $f, 'pattern', o );
                $f.data( 'vv-pattern', get_original_value_from_value( $f, 'alt' ) );
                removeAttr( $f, 'alt', o );
                validations.push( 'pattern' );
            }

            //	groups
            var grp = [ 'requiredgroup', 'validationgroup' ];
            for ( var g = 0, l = grp.length; g < l; g++ )
            {
	            var group = grp[ g ] + ':',
                	start = classes.indexOf( group );

                if ( start != -1 )
                {
	                var groupclass = classes.substr( start ).split( ' ' )[ 0 ],
	                    groupname = groupclass.substr( group.length );

	                if ( groupname.length )
	                {
	                    $f.removeClass( groupclass );
	                    $f.data( 'vv-' + grp[ g ], groupname );
	                    validations.push( grp[ g ]);
	                }
	            }
            }
        }

		//	password placeholder
		if ( $f.is( '[type="password"]' ) )
		{
			var d = ' ' + validations.join( ' ' ) + ' ';
			if ( d.indexOf( ' placeholder ' ) != -1 )
			{
				validations.push( 'passwordplaceholder' );
			}
		}

        //	add all remaining classes
        var classes = $f.attr( 'class' );
        if ( classes && classes.length )
        {
            validations.push( classes );
        }

        //	store validations
        $f.data( 'vv-validations', validations.join( ' ' ) );

        //	save original value
        $f.data( 'vv-original-value', original_value );
    }

    //	misc
    function has_validation( $f, v )
    {
        var d = ' ' + data( $f, 'validations' ) + ' ';
        return d.indexOf( ' ' + v + ' ' ) != -1;
    }
    function data( $f, d )
    {
        var v = $f.data( 'vv-' + d );
        if ( typeof v == 'undefined' )
        {
            v = '';
        }
        return v;
    }
    function removeAttr( $f, a, o )
    {
        if ( $.inArray( a, o.keepAttributes ) == -1 )
        {
            $f.removeAttr( a );
        }
    }
    function removeClass( $f, c, o )
    {
        if ( $.inArray( c, o.keepClasses ) == -1 )
        {
            $f.removeClass( c );
        }
    }
    function get_original_value( $f, o )
    {
        var val = get_outerHtml( $f );

        if ( $f.is( 'select' ) )
        {
            var num = 0,
            	dal = data( $f, 'placeholder-number' );

            if ( dal.length )
            {
	            num = dal;
            }
            else if ( typeof o.selectPlaceholder == 'number' )
            {
	            num = o.selectPlaceholder;
            }
            else
            {
	            $f.find( '> option' ).each(
	            	function( n )
	            	{
		                val = get_outerHtml( $(this) );
		                var qal = val.split( "'" ).join( '"' ).split( '"' ).join( '' );
		                qal = qal.substr( 0, qal.indexOf( '>' ) );
		
		                if ( qal.indexOf( 'selected=selected' ) > -1 )
		                {
		                    num = n;
		                }
		            }
		        );
            }
            $f.data( 'vv-placeholder-number', num );
            return get_original_value_from_value( $f.find( '> option:nth(' + num + ')' ) );

        }
        else if ( $f.is( 'textarea' ) )
        {
            val = val.substr( val.indexOf( '>' ) + 1 );
            val = val.substr( 0, val.indexOf( '</textarea' ) );
            return val;

        }
        else
        {
            return get_original_value_from_value( $f );
        }
    }
    function get_original_value_from_value( $f, at )
    {
        if ( typeof at == 'undefined' )
        {
            at = 'value';
        }
        var val = get_outerHtml( $f ),
            lal = val.toLowerCase();

        if ( lal.indexOf( at + '=' ) > -1 )
        {
            val = val.substr( lal.indexOf( at + '=' ) + ( at.length + 1 ) );
            var quot = val.substr( 0, 1 );
            if ( quot == '"' || quot == "'" )
            {
                val = val.substr( 1 );
                val = val.substr( 0, val.indexOf( quot ) );
            }
            else
            {
                val = val.substr( 0, val.indexOf( ' ' ) );
            }
            return val;
        }
        else
        {
            return '';
        }
    }
    function get_outerHtml( $e )
    {
        return $( '<div></div>' ).append( $e.clone() ).html();
    }
    function getclass( cl )
    {
        if ( typeof clss != 'undefined' && typeof clss[ cl ] != 'undefined' )
        {
            return clss[ cl ];
        }
        return cl;
    }
    function trim( str )
    {
        if ( str === null )
        {
            return '';
        }
        if ( typeof str == 'object' )
        {
        	var arr = [];
            for ( var a in str )
            {
                arr[ a ] = trim( str[ a ] );
            }
            return arr;
        }
        if ( typeof str != 'string' )
        {
            return '';
        }
        if ( str.length == 0 )
        {
            return '';
        }

        return str.replace(/^\s\s*/, '').replace(/\s\s*$/, '');
    }
    function strip_whitespace( str )
    {
        if ( str === null )
        {
            return '';
        }
        if ( typeof str == 'object' )
        {
            for ( var a in str )
            {
                str[ a ] = strip_whitespace( str[ a ] );
            }
            return str;
        }
        if ( typeof str != 'string' )
        {
            return '';
        }
        if ( str.length == 0 )
        {
            return '';
        }

        str = trim( str );

        var r = [ ' ', '-', '+', '(', ')', '/', '\\' ];
        for ( var i = 0, l = r.length; i < l; i++ )
        {
            str = str.split( r[ i ] ).join( '' );
        }
        return str;
    }
    function preventkeyup( kc )
    {
	    switch( kc ) {
            case 9:		//	tab
            case 13: 	//	enter
            case 16:	//	shift
            case 17:	//	control
            case 18:	//	alt
            case 37:	//	left
            case 38:	//	up
            case 39:	//	right
            case 40:	//	down
            case 224:	//	command
                return true;
                break;
            default:
            	return false;
            	break;
        }
    }


	//	DEPRECATED
	$.fn.validval = function( o, c, debug )
	{
		$.fn.validVal.deprecated( 'validval-method with all lowercase',  'validVal-method' );
		return this.validVal( o, c, debug );
	}
	//	/DEPRECATED


})( jQuery );