/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
var WEEK_WINDOWS_TIME=0;
var DAY_WINDOWS_TIME=1;
var QUERY_RESOLVER_URL='../queryResolver?id=';
var workload7daysMap={
    reale:{
        sies:'workloadReale7daysBySystem',
        sige:'workloadReale7daysBySystem',
        all:'workloadReale7daysAll'
    },
    cedacri:{
        gsy7:'',
        bsy2:'',
        csy3:'',
        zsy5:''
    },
    carige:{
        asdn:'',
        assv:''
            }};
var workloadByDayMap={
    reale:{
        sies:'workloadRealeByDaySystem',
        sige:'workloadRealeByDaySystem',
        all:'workloadRealeByDay'
    },
    cedacri:{
        gsy7:'',
        bsy2:'',
        csy3:'',
        zsy5:''
    },
    carige:{
        asdn:'',
        assv:''
            }
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
function getOptionsChartTransactionAnalysis(){
    return {
          chart: {
            zoomType: 'x',
            renderTo:'container'     },
        title: {
            text: ''
        },
        xAxis: {
            minRange:600*1000,
            minTickInterval:600*1000,
            type:'datetime'
        },tooltip: {
            shared:true
            
        },
        yAxis:[ {
                reversedStacks:false,
                 
            min: 0,
            title: {
                text: ''
            }
        },{
            min: 0,
            opposite:true,
            title: {
                text: '',
            style: {
                    color: Highcharts.getOptions().colors[0]
                }
            },
            labels:{
                style: {
                    color: Highcharts.getOptions().colors[0]
                }
            }
        }],
        plotOptions: {
            column: {
                stacking: 'normal'
            },
          series: {
                point: {
                    events: {
                    	click: function (e) {
                  }                      
        }}}   
        },
        series: []
    } ;
}
//disegna una baseline sul grafico highcharts
function drawBaseline(chart, value){
    optionsPlotline={
			zIndex: 5,
			color:'#e300e5',
			 id:'plotLine',
			 
                width: 1.5,
                value: value	
	};
    chart.yAxis[0].removePlotLine('plotLine');
    chart.yAxis[0].addPlotLine(optionsPlotline);    
} 
function drawTransactionAnalysis(optionsChart,url_json){
     $.getJSON(url_json,function(json){
             var series= [
            
            {
            
            color:'#0bff01',
            type: 'column',
            name: '',
            data: [],
            tooltip:{
                valueSuffix:''
            }
        }, {
            color:'#9ed670' ,
            type: 'column',
            name: '',
            data: [],
            tooltip:{
                valueSuffix:''
            }
        }, {
            color:'#e8d174',
            type: 'column',
            name: '',
            data: [],
            tooltip:{
                valueSuffix:''
            }
        },{
            color:'#e39e54',
            type: 'column',
            name: '',
            data: [],
            tooltip:{
                valueSuffix:''
            }
        }, {
            color:'#e8702a',
            type: 'column',
            name: 'Joe',
            data: [],
            tooltip:{
                valueSuffix:''
            }
        }, {
            color:'#d64d4d',
            type: 'column',
            name: '',
            data: [],
            tooltip:{
                valueSuffix:''
            }
        }, {
            color:'#bb1515',
            type: 'column',
            name: '',
            data: [],
            tooltip:{
                valueSuffix:''
            }
        },{
            yAxis:1,
            type: 'spline',
            name: '',
            data: [],
            tooltip:{
                valueSuffix:''
            }
        }];
             for(var i=1;i<json.dataLabel.length;i++){
                 series[i-1].name=json.dataLabel[i];
                 switch(series[i-1].yAxis){
                     case 1: series[i-1].tooltip.valueSuffix=' '+json.yAxis[1];
                             break;
                     default:series[i-1].tooltip.valueSuffix=' '+json.yAxis[0];
                             break;
                 }
                 
             }  
             json.data.forEach(function(element){
                 var d=dateToUTC(element[0]);
                 
                for(var l=1;l<element.length;l++){
                    series[l-1].data.push([d.getTime(),parseFloat(element[l])   ]);
             }   
               });
                optionsChart.yAxis[0].title.text=json.yAxis[0];
                optionsChart.yAxis[1].title.text=json.yAxis[1];
                optionsChart.series=series;
                chart = new Highcharts.Chart(optionsChart);
            }); 
}
function drawWorkload(windowsTime,optionsChart,istituto,system,url_tail){
    var url_json;
    switch(windowsTime){
        case WEEK_WINDOWS_TIME: url_json=workload7daysMap[istituto.toLowerCase()][system.toLowerCase()];
                                break;
        case DAY_WINDOWS_TIME: url_json=workloadByDayMap[istituto.toLowerCase()][system.toLowerCase()];
                                break;
    }
    system==='ALL' ? url_json=QUERY_RESOLVER_URL+url_json+url_tail : 
                     url_json=QUERY_RESOLVER_URL+url_json+'&system='+system+url_tail; 
    $.getJSON(url_json,function(json){
       optionsChart.series=[]; 
    var serie={
                   name:'',
                   data:[]
               };   
    json.data.forEach(function(element){
                     var date=dateToUTC(element[0]);
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

