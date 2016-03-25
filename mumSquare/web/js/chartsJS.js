/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
 Date.prototype.yyyymmdd = function() {
   var yyyy = this.getFullYear().toString();
   var mm = (this.getMonth()+1).toString(); // getMonth() is zero-based
   var dd  = this.getDate().toString();
   return yyyy +'-'+ (mm[1]?mm:"0"+mm[0]) +'-'+ (dd[1]?dd:"0"+dd[0]); // padding
  }; 
function getOptionsChartWorkload(){
    return  {
              chart: {
            type: 'areaspline',
            zoomType: 'x',
            renderTo: 'container'
        },
        style: {
        	position: 'absolute',
        	backgroundImage: 'loading.gif',
        	opacity: 0.5,
        	textAlign: 'center'
        },
        title: {
            text: ''
        },
             xAxis: {
            	 minTickInterval:3600*1000,
                 minRange:3600*1000,
            type: 'datetime',
            dateTimeLabelFormats: { // don't display the dummy year
                month: '%e. %b',
                year: '%b'
            },
            title: {
                text: 'Giornate'
            }
        },
        yAxis: {
            title: {
                text: 'MIPS'
            },
            min: 0
        },
        tooltip: {
            shared:true,
     	    formatter: function() {
 	    var s = '<b>'+ Highcharts.dateFormat("%A, %b %e, %H:%M",this.x) +'</b>';
        var total=0;
        $.each(this.points, function(i, point) {
            s += '<br/><span style="color:'+ point.series.color +'">\u25CF</span>: ' + point.series.name + ': ' + point.y;
            total+=point.y;
        });
         s += '<br/><span>\u25CF</span>: Total: ' + Math.floor(total);
        return s;
    }
        },
        plotOptions: {
               areaspline: {
                stacking: 'normal',
                lineColor: '#666666',
                lineWidth: 1,
                marker: {
                    radius: 2
                }
            },
            series:{}
            },
        series: []
         };
}

//disegna una baseline sul grafico highcharts
function drawBaseline(chart, value, baselineId){
    optionsPlotline={
			zIndex: 5,
			color:'#e300e5',
			 id:'plotLine',
			 
                width: 1.5,
                value: value	
	};
    chart.yAxis[0].removePlotLine(baselineId);
    chart.yAxis[0].addPlotLine(optionsPlotline);    
}

function drawWorkload(optionsChart,url_json,callback){
    $.getJSON(url_json,function(json){
       optionsChart.series=[]; 
    var serie={
                   name:'',
                   data:[]
               };
    var interval=json.data[0][0].length;
    json.data.forEach(function(element){
                     var d=element[0];
                     switch(interval){
                         case 16:
                             var date=new Date(Date.UTC(d.substring(0,4),//year
                                                parseInt(d.substring(5,7))-1,//month
                                                d.substring(8,10),//day
                                                d.substring(11,13), //hour
                                                d.substring(14,16) //minute
                                                ));
                             break;
                         case 13:
                             var date=new Date(Date.UTC(d.substring(0,4),//year
                                                parseInt(d.substring(5,7))-1,//month
                                                d.substring(8,10),//day
                                                d.substring(11,13) //hour
                                                ));
                             break;
                     }
                 
                     
                     
                     if(serie.name===''){
                         serie.name=element[1]; 
                         serie.data.push([date.getTime(),parseFloat(element[2])]);
                         
                     }   
                     else{
                       if(serie.name===element[1]){
                          serie.data.push([date.getTime(),parseFloat(element[2])]); 
                       }
                       else{
                           optionsChart.series.push(serie);
                           serie={
                                name:'',
                                data:[]
                                    };
                           serie.name=element[1];
                           serie.data.push([date.getTime(),parseFloat(element[2])]);
                       }
                     }
                });//end json.data.forEach
                optionsChart.series.push(serie);
                chart = new Highcharts.Chart(optionsChart);
                
});
}

