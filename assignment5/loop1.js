
const reduce = (f, acc, [x, ...xs], i = 0) => typeof x === 'undefined' ? acc : reduce(f, f(acc, x, i), xs, i + 1);

const find = (xs, q, from = 0) => reduce((acc, x, i) => acc * (i >= from && x == q && acc == -1 ? -i : 1), -1, xs, 0);

const find1 = (xs, q, from = 0) => reduce((acc, x, i) =>  i >= from && x == q && acc == -1 ? i : acc, -1, xs, 0);

const c = [ 'P', 'a', 'r', 'a', 'd', 'i', 'g', 'm', 's' ];
const a = "hello, world";
const b = [ 'P', 'a', 'r', 'a'];
const d = [];
//console.log(find1(c,'d',2));
const filter = (f, xs) => reduce((acc, x, i) => acc.concat(f(x, i) ? [ x ] : []), [], xs);


const sub = (xs, from, to = xs.length) => reduce((acc, x, i) =>  acc.concat( i >= from && i <= to ? [ x ] : []), [], xs, 0);
const sub1 = (xs, from, to = xs.length) => filter((acc, x, i) =>  acc.concat( i >= from && i <= to ? [ x ] : []),xs);
console.log(sub(c,3,4));
console.log(sub(a,3,4));
//
// const compose = (f, g) => (...args) => f(g.apply(null, args));
// const substr = str => compose(sub(from,to));
// console.log(substr(a, 2, 4));

const zip = (xs, ys) => reduce((acc, x, i) => acc.concat([[ x, ys[i] ]]), [], xs);
const equals = (xs, ys) => reduce((acc, x, i) => x[0]==x[1]? acc * 1 : acc*0,1,zip(xs,ys),0)
//const substr = compose(sub,null);
console.log(zip(d,b));
console.log(equals(d,b));
//console.log(substr(a,3,5));
