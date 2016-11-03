
  function setOptions(json){
      
    var colors = Highcharts.getOptions().colors;
    var categories=[];
    var tot=0;
    var hour=json.data[0][3];
    var actualCategory=null; 
    var data=[];
    var mipsAvailable={
                color:"",
                y:0,
               drilldown:{ name:"Disponibili",
                categories:[],
                data:[]}
            };
    var initializeData=function(el){
        actualCategory=el[0];
        categories.push(actualCategory);
        data.push({
                color:colors[data.length],
                y:Number(el[2]),
               drilldown:{ name:actualCategory,
                categories:[el[1]],
                data:[Number(el[2])]}
            });
        mipsAvailable.y=mipsAvailable.y+(Number(el[4])-Number(el[2]));
        mipsAvailable.drilldown.categories.push(actualCategory);
        mipsAvailable.drilldown.data.push(Number(el[4])-Number(el[2]));
    };
    for(var i=0;i<json.data.length;i++){
        tot+=Number(json.data[i][2]);
        if(i===0){
            initializeData(json.data[i]);
        }else{
            if(actualCategory===json.data[i][0]){
                 var dataIndex=data.length-1;
                 data[dataIndex].drilldown.categories.push(json.data[i][1]);
                 data[dataIndex].drilldown.data.push(Number(json.data[i][2]));
                 data[dataIndex].y=data[dataIndex].y+Number(json.data[i][2]);
                 mipsAvailable.y=mipsAvailable.y-Number(json.data[i][2]);
                 mipsAvailable.drilldown.data[dataIndex]=mipsAvailable.drilldown.data[dataIndex]-Number(json.data[i][2]);
            }else{
               initializeData(json.data[i]);
            }
            }
     }
    categories.push("Disponibili");
    mipsAvailable.color=colors[data.length];
    data.push(mipsAvailable);
    //console.log(data);
    var machineData = [],
    systemData = [],
    i,
    j,
    dataLen = data.length,
    drillDataLen,
    brightness;    
        // Build the data arrays
    for (i = 0; i < dataLen; i += 1) {
        // add browser data
        machineData.push({
            name: categories[i],
            y: data[i].y,
            color: data[i].color
        });
        // add version data
        drillDataLen = data[i].drilldown.data.length;
        for (j = 0; j < drillDataLen; j += 1) {
            brightness = 0.2 - (j / drillDataLen) / 5;
            systemData.push({
                name: data[i].drilldown.categories[j],
                y: data[i].drilldown.data[j],
                color: Highcharts.Color(data[i].color).brighten(brightness).get()
            });
        }};
    return {
    		            	  chart: {
    		            renderTo:'container',
    		            type: 'pie'
    		            
    		        },
    		        title: {
    		            text: 'Consumo totale: '+tot+' /  Ora di picco: '+hour,
    		            align:'left',
    		            style:{ "color": "#333333", "fontSize": "11px" }
    		        },
    		        plotOptions: {
    		            pie: {
    		                shadow: false,
    		                center: ['50%', '50%']
    		            }
    		        },
    		        tooltip: {
    		            valueSuffix: ' MIPS',
    		            pointFormat:'<span style="color:{point.color}">\u25CF</span> {series.name}: <b>{point.y:.0f}</b><br/>'
    		        },
    		        series: [{
    		            name: 'Machine',
    		            data: machineData,
    		            size: '60%',
    		            dataLabels: {
    		                formatter: function () {
    		                    return this.point.name;
    		                },
    		                color: 'white',
    		                distance: -30
    		            }
    		        }, {
    		            name: 'System',
    		            data: systemData,
    		            size: '80%',
    		            innerSize: '60%',
    		            dataLabels: {
    		                formatter: function () {
    		                     return this.y > 1000 ? '<b><span style="color:'+this.point.color +'">?</span>' + this.point.name + ':</b> ' + this.y   : null;
    		                }
    		             
    		            }
    		        }]
    		            };
}
  $(function () 
    		{
    	        var chart;
    		$.getJSON('/queryResolver?id=lastDayMips',function(json){
                    chart = new Highcharts.Chart(setOptions(json));
    			  });
    		});
