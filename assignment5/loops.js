var array = [ 8, -6, 7, -5, 3, 0, -9 ];

function reduce(array, f, accumulator)
{
	if(array.length == 0)
		return accumulator;
	
	var firstElement = array[0];
	var restOfArray = array.slice(1);
	
	return reduce(restOfArray, f, f(accumulator, firstElement));
}

function map(array, f)
{
	if(array.length == 0)
		return [];
	
	var firstElement = array[0];
	var restOfArray = array.slice(1);
	
	return [ f(firstElement) ].concat(map(restOfArray, f));
}

function forEach(array, f)
{
	if(array.length == 0)
		return;
	
	var firstElement = array[0];
	var restOfArray = array.slice(1);
	
	f(firstElement);
	forEach(restOfArray, f);
}

function filter(array, f)
{
	if(array.length == 0)
		return [];
	
	var firstElement = array[0];
	var restOfArray = array.slice(1);
	
	if(f(firstElement))
		return [ firstElement ].concat(filter(restOfArray, f));
	else
		return filter(restOfArray, f);
}

function compose(f, g)
{
	return function()
	{
		return f(g.apply(null, arguments));
	};
}

function sumItUp(array)
{
	return reduce(array, function(accumulator, x)
	{
		return accumulator + x;
	}, 0);
}

function doubleValues(array)
{
	return map(array, function(x)
	{
		return x * 2;
	});
}

function isPositive(x)
{
	return x >= 0;
}

function count(array)
{
	return array.length;
}

function countPositive(array)
{
	return count(filter(array, isPositive));
}

console.log(sumItUp(array));
console.log(countPositive(array));
console.log(doubleValues(array));

var doubleAndCountPositive = compose(countPositive, doubleValues);
console.log(doubleAndCountPositive(array));

console.log(filter(array, isPositive));

forEach(array, function(x)
{
	console.log(x);
});
