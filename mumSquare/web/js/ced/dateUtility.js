function shiftDate(amount,date,callback,params){
         date.setDate(date.getDate()+amount);
         callback(params);
     }
 function dateUTCtoString(milliseconds){
     d=new Date(0);
     d.setUTCMilliseconds(milliseconds);
     d.setTime( d.getTime() + d.getTimezoneOffset()*60*1000 );
    return d;
 }
 function dateToUTC(date){
     switch(date.length){
         case 13:return new Date(Date.UTC(date.substring(0,4),//year
                                                parseInt(date.substring(5,7))-1,//month
                                                date.substring(8,10),//day
                                                date.substring(11,13) //hour
                                                ));
        case 16:return new Date(Date.UTC(date.substring(0,4),//year
                                                parseInt(date.substring(5,7))-1,//month
                                                date.substring(8,10),//day
                                                date.substring(11,13), //hour
                                                date.substring(14,16) //minute
                                                ));
     }
     
 }
 Date.prototype.yyyymmdd = function() {
   var yyyy = this.getFullYear().toString();
   var mm = (this.getMonth()+1).toString(); // getMonth() is zero-based
   var dd  = this.getDate().toString();
   var hh  = this.getHours().toString();
   var min  = this.getMinutes().toString();
   return yyyy +'-'+ (mm[1]?mm:"0"+mm[0]) +'-'+ (dd[1]?dd:"0"+dd[0])+" "+(hh[1]?hh:"0"+hh[0])+":"+(min[1]?min:"0"+min[0]); // padding
  }; 
