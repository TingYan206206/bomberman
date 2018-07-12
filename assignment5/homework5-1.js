// Change this variable to true if you want to automatically test the bonus portion as well.
const tryBonus = true;

//-----------------------------------------------------------------------------------
// Functional toolkit.
//-----------------------------------------------------------------------------------

// Reduce an array or sequence to a single value.
// f:          a callback of the form (accumulator, element, index) => newAccumulator
// acc:        the starting accumulator value
// [x, ...xs]: the array or sequence
// i:          the current index in the reduction; this will be passed to f
const reduce = (f, acc, [x, ...xs], i = 0) => typeof x === 'undefined' ? acc : reduce(f, f(acc, x, i), xs, i + 1);

// Map each element of an array or sequence to a new value.
// f:  a callback of the form (accumulator, element, index) => newValue
// xs: the array or sequence
const map = (f, xs) => reduce((acc, x, i) => acc.concat([ f(x, i) ]), [], xs);

// Filter an array or sequence.
// f:  a callback of the form (accumulator, element, index) => whetherToKeepElement
// xs: the array or sequence
const filter = (f, xs) => reduce((acc, x, i) => acc.concat(f(x, i) ? [ x ] : []), [], xs);

// Combine two arrays or sequences into a multidimensional array.
// xs: one array or sequence
// ys: the other array or sequence
const zip = (xs, ys) => reduce((acc, x, i) => acc.concat([[ x, ys[i] ]]), [], xs);

// Combine two functions into one new function.
// f: one function
// g: the other function
const compose = (f, g) => (...args) => f(g.apply(null, args));

//-----------------------------------------------------------------------------------
// Functions to implement. You may use any of the above functions as well as concat
// on arrays and any operator (+, ==, etc), but you may not use any other function,
// and all of your functions must be pure functional programming functions.
//-----------------------------------------------------------------------------------

// Get the length of an array or sequence.
// xs: the array or sequence
// Hint: use reduce
const length = xs => reduce((acc,x,i)=>acc + 1, 0, xs, 0);

// Check whether two arrays or sequences are equal.
// xs: one array or sequence
// ys: the other array or sequence
// Hint: use reduce and zip
//const equals = (xs, ys) =>  reduce ((acc, x, i) =>  x==ys[i]? acc * 1 : acc*0, 1, xs, 0);
const equals = (xs, ys) => reduce((acc, x, i) =>  x[0]==x[1]? acc * 1 : acc*0,1,zip(xs,ys),0)
// Concatenate an array or sequence into a string.
// xs: the array or sequence
// Hint: use reduce
const stringify = xs => reduce ((acc, x, i) => acc.concat(x), "", xs, 0);

// Get a sub-array or sub-sequence.
// xs:   the full array or sequence
// from: the first index to include in the return
// to:   the last index to include in the return; defaults to xs.length
// Hint: use filter
//const sub = (xs, from, to = xs.length) => reduce((acc, x, i) =>  acc.concat( i >= from && i <= to ? [ x ] : []), [], xs, 0);
const sub = (xs, from, to = xs.length) => filter((x,i)=> i>= from && i<=to ,xs)
// Reverse an array or sequence.
// xs: the array or sequence
// Hint: use reduce
const reverse = xs => reduce ((acc, x, i) => x.concat(acc), [], xs, 0);

// Find the index of the first occurrence of an element in an array or sequence
// Returns -1 if the element is not found in the array or sequence.
// xs:   the array or sequence
// q:    the element to find
// from: the first index to check; defaults to 0
// Hint: use reduce
const find = (xs, q, from = 0) => reduce((acc, x, i) => acc * (i >= from && x == q && acc == -1 ? -i : 1), -1, xs, 0);

// Find the index of the first occurrence of anything except a given element in an array or sequence
// Returns -1 if the array or sequence is empty or if it is entirely made up of the given element.
// xs:   the array or sequence
// q:    the element to NOT find
// from: the first index to check; defaults to 0
// Hint: use reduce
const findNot   = (xs, q, from = 0) => reduce((acc, x, i) => acc * (i >= from && x != q && acc == -1 ? -i : 1), -1, xs, 0);;

// Get a substring.
// Hint: use compose instead of a lambda function

const substr = compose(stringify,sub);

// Reverse a string.
// Hint: use compose instead of a lambda function

const revstr = compose(stringify,reverse);

// Remove leading spaces from a string.
// str: the string
// Hint: use the other functions you wrote
const ltrim = str => stringify(sub(str, findNot(str," ",0), length(str)));

// Remove trailing spaces from a string.
// Hint: use compose instead of a lambda function
//const re = compose(ltrim,revstr);
const rtrim = compose(revstr,compose(ltrim,revstr));

// Remove both leading and trailing spaces from a string.
// Hint: use compose instead of a lambda function
const trim = compose(ltrim, rtrim);

//-----------------------------------------------------------------------------------
// Optional bonus functions to implement, using the same rules as above.
//-----------------------------------------------------------------------------------

// Combine an array of strings into a single string, with a delimiter in-between.
// xs: the array of strings
// delim: the delimiter to use between elements of xs; defaults to " "
// Hint: use reduce
const join = (xs, delim = " ") => reduce ((acc, x, i) => i == xs.length-1 ? acc.concat(x) : acc.concat(x+ delim), "", xs, 0);;

// Split a string into an array of strings using a delimiter.
// xs: the array of strings
// delim: the delimiter on which to split
// Hint: this probably requires at least two lines of code; use the other functions you wrote
const split = (str, delim = " ") => [];

// Wrap a string in an HTML tag.
// For example, tag("test", "span") should return "<span>test</span>".
// str: the string
// tag: the name of the tag
// Hint: don't overthink this
const tag = (str, tag) => "<" + tag +">" + str + "</" + tag + ">";

// Parse a line of text to convert leading hash tags to a header HTML tag.
// For example...
//     header("line") should return "line",
//     header("# line") should return "<h1>line</h1>",
//     header("## line") should return "<h2>line</h2>",
//     etc.
// line: the line to parse
// Hint: this probably requires at least two lines of code; use the other functions you wrote
const header = line => line;

// Parse a single word of text to add emphasizing HTML tags.
// This includes...
//     em: one star on either end of the word should produce an em tag.
//         i.e. emphWord("*word*") should return "<em>word</em>"
//     strong: two stars should produce a strong tag.
//         i.e. emphWord("**word**") should return "<strong>word</strong>"
//     both: three stars should produce both an em and a strong tag.
//         i.e. emphWord("***word***") should return "<strong><em>word</em></strong>"
//     more: more than 3 stars should produce an em and a strong tag, leaving extra stars alone.
//         i.e. emphWord("****word****") should return "<strong><em>*word*</em></strong>"
//     none: no stars should have no effect.
//         i.e. emphWord("word") should return "word"
//     uneven: if the number of stars is uneven, use the smaller option.
//         i.e. emphWord("*word") should return "*word"
//         i.e. emphWord("**word*") should return "<em>*word</em>"
//         i.e. emphWord("**word***") should return "<strong>word*</strong>"
// Hint: this one is tricky, and probably requires several lines of code (~5); use the other functions you wrote
const emphWord = word => word;

// Parse a line of text to add emphasizing HTML tags by calling emphWord.
// line: The line to parse (must be split into multiple words).
// Hint: use map with some of the other functions you wrote
const emph = line => line;

// Parse several lines of text to add HTML header and emphasizing tags by calling header and emph.
// str: The string to parse (must be split into multiple lines).
// Hint: use map, compose, and some of the other functions you wrote
const parse = str => join(map(compose(header, emph), split(str, "\n")), "\n");

//-----------------------------------------------------------------------------------
// Test the required functions. If these tests pass and your functions are written
// only in terms of the functions above, then your homework is complete.
//-----------------------------------------------------------------------------------

// Note: you should probably not edit the code past this line

function test(f, args, x, eq = (a, b) => a == b)
{
	const r = f.apply(null, args);
	if(!eq(r, x))
	{
		throw f.name + " failed!\n" +
			"\tArgs: " + JSON.stringify(args) + "\n" +
			"\tExpected " + JSON.stringify(x) + ", got " + JSON.stringify(r);
	}
}

const a = "hello, world";
const b = "goodbye";
const c = [ 'P', 'a', 'r', 'a', 'd', 'i', 'g', 'm', 's' ];
const d = [ 'r', 'A', 'w', 'K', 's', '!' ];

try
{
	test(length, [a], 12);
	test(length, [b], 7);

	test(equals, [a, a], true);
	test(equals, [a, b], false);
	test(equals, [a, []], false);
	//test(equals, [[], a], false);

	test(stringify, [c], "Paradigms");
	test(stringify, [d], "rAwKs!");

	test(sub, [c, 2, 4], "rad", equals);
	test(sub, [c, 1, 7], "aradigm", equals);

	test(reverse, [c], "smgidaraP", equals);
	test(compose(reverse, reverse), [c], c, equals);

	test(find, [c, 'a'], 1);
	test(find, [d, '?'], -1);

	test(findNot, [d, 'r'], 1);
	test(findNot, [[], 1], -1);

	test(substr, [a, 3, 7], "lo, w");
	test(substr, [b, 0, 3], "good");

	test(revstr, [a], "dlrow ,olleh");
	test(revstr, [b], "eybdoog");

	test(ltrim, ["     " + a], a);
	test(ltrim, [b], b);

	test(rtrim, [a + "     "], a);
	test(rtrim, [b], b);

	test(trim, ["    " + a], a);
	test(trim, [a + "    "], a);
	test(trim, ["    " + a + "     "], a);
	test(trim, [b], b);

	console.log("All tests passed! Well done!");
}
catch(e)
{
	if(typeof e === 'string')
		console.error(e);
	else
		throw e;
}

//-----------------------------------------------------------------------------------
// Test the bonus functions. If these tests pass and your functions are written
// only in terms of the functions above, then your homework bonus is complete.
// You must set tryBonus to true (at the top) in order to run these tests.
//-----------------------------------------------------------------------------------

if(tryBonus)
{
	try
	{
		test(join, [[ "this", "is", "a", "test" ], " "], "this is a test");
		test(join, [[ "no", "spaces", "here" ], ""], "nospaceshere");

		test(split, [" this  is a test "], ["", "this", "", "is", "a", "test", ""], equals);
		test(split, ["this is another test"], ["this", "is", "another", "test"], equals);

		test(tag, ["hi", "b"], "<b>hi</b>");
		test(tag, ["hi", "strong"], "<strong>hi</strong>");

		test(header, ["No header"], "No header");
		test(header, ["# Header Level One"], "<h1>Header Level One</h1>");
		test(header, ["## Header Level Two"], "<h2>Header Level Two</h2>");

		test(emphWord, ["word"], "word");
		test(emphWord, ["*word*"], "<em>word</em>");
		test(emphWord, ["**word**"], "<strong>word</strong>");
		test(emphWord, ["***word***"], "<strong><em>word</em></strong>");
		test(emphWord, ["****word****"], "<strong><em>*word*</em></strong>");
		test(emphWord, ["*word****"], "<em>word***</em>");
		test(emphWord, ["**word*"], "<em>*word</em>");
		test(emphWord, ["***word**"], "<strong>*word</strong>");

		test(emph, ["this is a string, after all"], "this is a string, after all");
		test(emph, ["this *is* a **string,** after all"], "this <em>is</em> a <strong>string,</strong> after all");

		test(parse, [`
# Introduction

This is my first parser*.

*May or may not actually by my first parser.

## Headers

This parser handles headers based on the number of hash tags at the beginning of a line.

## Emphases

This parser also allows me to emphasize words.
If I put one star on either side of a word, it becomes *italicized.*
If I put two stars, it becomes **bold,** and if I use three, it will be ***both*** bold and italics.
More than that and the parser just ****leaves**** the extra stars alone.

## Caveats

Unfortunately, *I cannot italicize multiple words at once just yet.*`], `
<h1>Introduction</h1>

This is my first parser*.

*May or may not actually by my first parser.

<h2>Headers</h2>

This parser handles headers based on the number of hash tags at the beginning of a line.

<h2>Emphases</h2>

This parser also allows me to emphasize words.
If I put one star on either side of a word, it becomes <em>italicized.</em>
If I put two stars, it becomes <strong>bold,</strong> and if I use three, it will be <strong><em>both</em></strong> bold and italics.
More than that and the parser just <strong><em>*leaves*</em></strong> the extra stars alone.

<h2>Caveats</h2>

Unfortunately, *I cannot italicize multiple words at once just yet.*`);

		console.log("All bonus tests passed! Excellent work!");
	}
	catch(e)
	{
		if(typeof e === 'string')
			console.error(e);
		else
			throw e;
	}
}
