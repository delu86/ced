/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

var HIGHCHART_TYPE=0;
var MORRIS_CHART_TYPE=1;
function createChart(jsonId,divId,chartType){
    
    switch (chartType){
        
                         case HIGHCHART_TYPE: createHighchart(jsonId,divId);
                             break;
                         case MORRIS_CHART_TYPE:createMorrischart(jsonId,divId); 
    }

    }

function createHighchart(jsonId,divId){
      $.getJSON('queryResolver?id='+jsonId+'&system=SIES',function(json){
      var options={
        title: {
            text: '',
            x: -20 //center
        },
        subtitle: {
            text: '',
            x: -20
        },
        xAxis: {
            type:'category'
        },
        yAxis: {
            
        },
        tooltip: {
            
        },
        legend: {
            layout: 'vertical',
            align: 'right',
            verticalAlign: 'middle',
            borderWidth: 0
        },
        series: []
    };
      //set chart title
      if(!(json.view.title===null||json.view.title===undefined)){
          options.title.text=json.view.title;
      }
      //set chart subtitle
      if(!(json.view.subtitle===null||json.view.subtitle===undefined)){
          options.title.text=json.view.subtitle;
      }
      //set chart xAxis type
      if(!(json.view.xAxis.type===null||json.view.xAxis.type===undefined)){
          options.xAxis.type=json.view.xAxis.type;
      }
      //set tooltip suffix 
      if(!(json.view.unit_measure===null||json.view.unit_measure===undefined)){
          options.tooltip.suffix=json.view.unit_measure;
      }
      //if xAxis type is category 
      if(options.xAxis.type==='category'){
          //set up xAxis categories; if there's no oxAxis  
            var series=[];
            json.data[0].forEach(function(){
                series.push([]);
            });
            json.data.forEach(function(val,ind,arr){
               json.data[ind].forEach(function(val2,ind2,arr2){
                  series[ind2].push(val2);
               }); 
            });
            
          }
      
      
    });

function createMorrischart(jsonId,divId){
    
}
}