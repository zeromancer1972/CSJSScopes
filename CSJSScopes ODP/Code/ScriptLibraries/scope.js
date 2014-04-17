/**
 * Fetch scoped variable value
 * 
 * @return the value
 */
function getScopeValue(scope, key, targetId) {
	require( [ "dojo/request/xhr" ], function(xhr) {
		xhr("service.xsp", {
			handleAs : "json",
			sync : true,
			method : "GET",
			query : {
				"scope" : scope,
				"key" : key
			}
		}).then( function(data) {
			dojo.byId(targetId).value = data.value;
		}, function(error) {
			alert(error);
		});
	});
}

/**
 * Set scoped variable value
 * 
 * @param scope
 * @param key
 * @param value
 */
function setScopeValue(scope, key, value) {
	require( [ "dojo/request/xhr" ], function(xhr) {
		xhr("service.xsp", {
			sync : true,
			method : "POST",
			data : {
				"scope" : scope,
				"key" : key,
				"value" : value
			}
		}).then( function(data) {

		}, function(error) {
			alert(error);
		});
	});

}