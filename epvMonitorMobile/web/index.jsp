<%
response.setHeader("Cache-Control","no-cache");
response.setHeader("Cache-Control","no-store");
response.setHeader("Pragma","no-cache");
response.setDateHeader ("Expires", 0);
%>

	<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<link rel="stylesheet" href="CSS/jquery.mobile-1.4.4.min.css" />
	<script src="scripts/jquery-1.11.1.min.js"></script>
	<script src="scripts/jquery.mobile-1.4.4.min.js"></script>
			<script>
		// Bind to "mobileinit" before you load jquery.mobile.js
		// Set the default transition to slide
		$(document).on( "mobileinit", function() {
			$.mobile.defaultPageTransition = "slide";
		});	
	</script>

	<script>
		$( document ).on( "pageinit", "[data-role='page'].pageSwipe", function() {
			var page = "#" + $( this ).attr( "id" ),
				// Get the filename of the next page that we stored in the data-next attribute
				next = "#"+$( this ).jqmData( "next" ),
				// Get the filename of the previous page that we stored in the data-prev attribute
				prev = "#"+$( this ).jqmData( "prev" );
			
			// Check if we did set the data-next attribute
			if ( next ) {
				// Prefetch the next page
				$.mobile.loadPage( next  );
				// Navigate to next page on swipe left
				$( document ).on( "swipeleft", page, function() {
					$.mobile.changePage( next  );
				});
				// Navigate to next page when the "next" button is clicked
				$( ".next", page ).on( "click", function() {
					$.mobile.changePage( next  );
				});
			}
			// Disable the "next" button if there is no next page
			else {
				$( ".next", page ).addClass( "ui-disabled" );
			}
			// The same for the previous page (we set data-dom-cache="true" so there is no need to prefetch)
			if ( prev ) {
				$( document ).on( "swiperight", page, function() {
					$.mobile.changePage( prev , { reverse: true } );
				});
				$( ".prev", page ).on( "click", function() {
					$.mobile.changePage( prev , { reverse: true } );
				});
			}
			else {
				$( ".prev", page ).addClass( "ui-btn-disabled" );
			}
		});
    </script>

<title>Cedacri EpvMonitor</title>
<link rel="shortcut icon" href="img/favicon.ico" />
</head>
<body>
<jsp:include page="dashboard.jsp" /><!--Summary-->
<jsp:include page="focalPoint.jsp" /><!--Perl Agent-->
<jsp:include page="agent.jsp" /><!--Perl Agent-->
<jsp:include page="smf.jsp" /><!--SMF-->
<jsp:include page="switchdb.jsp" /><!--Switch DB-->
<jsp:include page="mysql.jsp" /><!--Galleria immagini-->
</body>
</html>