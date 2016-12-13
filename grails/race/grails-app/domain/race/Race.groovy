package race

class Race {

    static constraints = {
		name(blank:false,maxSize:50)           
		startDate(validator:{return (it > new Date())})          
		city(inList:["guangzhou","foshan","dongguan","shenzhen"])            
		state(inList:["GA","NC","SC","VA"])           
		distance(min:0.0)          
		cost(min:0.0,max:100.0)
		maxRunners(min:0,max:100000) 
    }
	
	 static mapping = {                 
		sort "startDate"         
	 } 
	 
	String name      
	Date startDate    
	String city      
	String state
	BigDecimal distance       
	BigDecimal cost       
	Integer maxRunners
	static hasMany = [registration:Registration]

}
