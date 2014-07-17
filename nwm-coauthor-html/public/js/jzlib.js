function getSchemaDisplay(schema) {
	var display = [];

	angular.forEach(schema, function(value, key) {
		if (!isPromiseOrResolved(key)) {
			var obj = {};

			obj['name'] = key;
			obj['displayOrder'] = value.displayOrder;
			obj['displayName'] = value.displayName;

			display.push(obj);
		}
	});

	return display;
}