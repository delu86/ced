/**
 * 
 */
 $(function(){
		Highcharts.setOptions({
	        lang: {
	            numericSymbols: null,
	            thousandsSep: '\''
	        }
	    });
     $(".shift-right-one").prop('disabled',true);
	 var offset=1;
	 var date;
		setDate();
     var system=$( "button[autofocus='true']" ).val();
     var optionsDrillDown={
    		 chart: {
     	        renderTo: 'container',
     	        },exporting: {
     	           buttons: {
     	              backButton: {
     	                  text: '< <b>Back',
     	                  onclick: function () {
     	                  	chart = new Highcharts.Chart(options);
     	             	   	 return true;
     	               	
     	                  }}}},
     	        yAxis: [{
     	            min: 0,
     	            title: {
     	                text: 'Volumi',
     	                style: {
     	                    color: Highcharts.getOptions().colors[0]
     	                }
     	            }, labels: {
     	                format: '{value}',
     	                style: {
     	                    color: Highcharts.getOptions().colors[0]
     	                }
     	            },
     	            stackLabels: {
     	                enabled: true,
     	                style: {
     	                    fontWeight: 'bold',
     	                    color: Highcharts.getOptions().colors[0]
     	                }
     	            }
     	        },{ min: 0,
     	        	allowDecimals: false,
     	            labels: {
     	                format: '{value}',
     	                style: {
     	                    color: Highcharts.getOptions().colors[1]
     	                }
     	            },
     	            title: {
     	                text: 'CPU time',
     	                style: {
     	                    color: Highcharts.getOptions().colors[1]
     	                }
     	            },opposite: true
     	            
     	        },{ min: 0,
    	        	
    	            
    	        	labels: {
    	                format: '{value}',
    	                style: {
    	                    color: Highcharts.getOptions().colors[3]
    	                }
    	            },
    	            title: {
    	                text: 'Efficienza',
    	                style: {
    	                    color: Highcharts.getOptions().colors[3]
    	                },
    	            },opposite: true
    	            
    	        }],  xAxis: {
    	        	title:{
    	        		text:'Ora'
    	        	},
     	        	   labels: {
     	      	        	 
     	    	             rotation: -45},
     	        	gridLineWidth: 1,
     	        	tickInterval:1,
     	            tickmarkPlacement: 'on',
     	         categories: []
     	     },    title: {
     	    	 text:'',
     	         x: -20 //center
     	     },tooltip: {
     	         formatter: function () {
     	             var s = '<b>' + this.x + '</b>';
     	             $.each(this.points, function () {
     	                 s += '<br/>' + '<span style="color:' + this.series.color + '"> ■</span>' + ' ' + this.series.name + ': ' + this.y ;
     	             });
     	             return s;
     	         },
     	         shared: true
     	     },  	     
     	     series: [{name:'Volumi',type:'column',
     	    	 data:[]
     	            },{
     	            	name:'Cpu Time',yAxis:1,type:'spline',data:[]
     	            },{
    	            	name:'Efficienza',yAxis:2,type:'spline',data:[],color:Highcharts.getOptions().colors[3]
    	            }]	 
     }
     var options={
    		    
            chart: {
    	        renderTo: 'container',
    	        },
    	        title:{
    	        	text: ''
    	        },
    	        yAxis: [{
    	            min: 0,
    	            title: {
    	                text: 'Volumi',
    	                style: {
    	                    color: Highcharts.getOptions().colors[0]
    	                }
    	            }, labels: {
    	                format: '{value}',
    	                style: {
    	                    color: Highcharts.getOptions().colors[0]
    	                }
    	            },
    	            stackLabels: {
    	                enabled: true,
    	                style: {
    	                    fontWeight: 'bold',
    	                    color: Highcharts.getOptions().colors[0]
    	                }
    	            }
    	        },{ min: 0,
    	        	allowDecimals: false,
    	            labels: {
    	                format: '{value}',
    	                style: {
    	                    color: Highcharts.getOptions().colors[1]
    	                }
    	            },
    	            title: {
    	                text: 'CPU time',
    	                style: {
    	                    color: Highcharts.getOptions().colors[1]
    	                }
    	            },opposite: true
    	            
    	        },{ min: 0,
    	        	
    	            
    	        	labels: {
    	                format: '{value}',
    	                style: {
    	                    color: Highcharts.getOptions().colors[3]
    	                }
    	            },
    	            title: {
    	                text: 'Efficienza',
    	                style: {
    	                    color: Highcharts.getOptions().colors[3]
    	                },
    	            },opposite: true
    	            
    	        }],  xAxis: {
    	        	title:{
    	        		text:'APPLID'
    	        	},
    	        	   labels: {
    	      	        	 
    	    	             rotation: -45},
    	        	gridLineWidth: 1,
    	        	tickInterval:1,
    	            tickmarkPlacement: 'on',
    	         categories: []
    	     },    title: {
    	    	 text:'',
    	         x: -20 //center
    	     },tooltip: {
    	         formatter: function () {
    	             var s = '<b>' + this.x + '</b>';
    	             $.each(this.points, function () {
    	                 s += '<br/>' + '<span style="color:' + this.series.color + '"> ■</span>' + ' ' + this.series.name + ': ' + this.y ;
    	             });
    	             return s;
    	         },
    	         shared: true
    	     },  	
    	     plotOptions: {
    	         
    	     series:{point: {
	         	  events: {
	               	click: function (e) {
	               		$('#loading').show();
	               		$.getJSON('../queryResolver?id=cicsEfficienza/cicsEfficienzaBySystemDateId&date='+date+'&applvtname='+e.point.category+'&system='+system,function(json){
	               	    optionsDrillDown.title.text=system+": "+e.point.category;
	               	data=[[],[],[],[]];
                        json.data.forEach(function(el){
                            data[0].push(el[0]);
                            data[1].push(Number(el[2]));
                            data[2].push(Number(el[1]));
                            data[3].push(Number(el[2])/Number(el[1]));
                        });
                        optionsDrillDown.xAxis.categories=data[0];
  			optionsDrillDown.series[0].data=data[1];
  			optionsDrillDown.series[1].data=data[2];
  			optionsDrillDown.series[2].data=data[3];   
	             		chart = new Highcharts.Chart(optionsDrillDown);
	             		$('#loading').hide(); 
	             		return true;
	             		});
	               	}}},
	             pointPadding: 0.2,
	             borderWidth: 0
	         }
    	     },
    	     series: [{name:'Volumi',type:'column',
    	    	 data:[]
    	            },{
    	            	name:'Cpu Time',yAxis:1,type:'spline',data:[]
    	            },{
    	            	name:'Efficienza',yAxis:2,type:'spline',data:[],color:Highcharts.getOptions().colors[3]
    	            }]};
     
     createChart();
     $(".shift-left-one").click(function(){
     	offset+=1;
     	setDate();
     	if(offset==2)
     		$(".shift-right-one").prop('disabled',false);
     	createChart();
     });
     $(".shift-right-one").click(function(){
     	offset-=1;
     	setDate();
     	if(offset==1)
     		$(".shift-right-one").prop('disabled',true);
     	createChart();
     });
	   $( ".target" ).click(function() {
		   
		   system=$(this).val();
		    createChart();
	});
     function setDate(){
     	var d1=new Date();
      	d1.setDate(d1.getDate()-offset);
      	
      	date=$.datepicker.formatDate('yy-mm-dd', d1);
      	$("#date-interval").text($.datepicker.formatDate('dd/mm/yy', d1));
     };
     function createChart(){
     	$('#loading').show();
     $.getJSON('../queryResolver?id=cicsEfficienza/cicsEfficienzaBySystemDate&system='+system+'&date='+date, function(json){
     	
                        data=[[],[],[],[]];
                        json.data.forEach(function(el){
                            data[0].push(el[0]);
                            data[1].push(Number(el[2]));
                            data[2].push(Number(el[1]));
                            data[3].push(Number(el[2])/Number(el[1]));
                        });
                        options.xAxis.categories=data[0];
  			options.series[0].data=data[1];
  			options.series[1].data=data[2];
  			options.series[2].data=data[3];
  			options.title.text=system;
  			chart = new Highcharts.Chart(options);
        	$('#loading').hide();
    	    return true;
       });
     }
 })