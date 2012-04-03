// display current time at author location
// =======================================
// copyright Stephen Chapman, Felgall Pty Ltd, 11 July 2001, 25th November 2004
// http://www.felgall.com/ and http://javascript.about.com/
// permission is given to use this script
// provided that all comment lines in the script are retained

function myTime() {
var dst = 0;       // set to 1 for daylight savings time
                   // update this as you go on and off daylight saving time

var loc = 'Santiago, Chile'; // set to your location
var mtz = -4;      // set to your local timezone (hours ahead of UTC, negative if behind)
var stdz = 'AEST'; // standard time indicator
var dayz = 'ADST'; // daylight saving time indicator (blank if you dont have daylight saving)

// do not alter anything below this line
document.writeln('The time in ' + loc + ' is <span id="time">' + setDsp(mtz,dst,stdz,dayz) + '<\/span>');
if (DOMsupported) setTimeout('updDsp('+mtz+',' +dst+',"' +stdz+'","' +dayz+'")', 5000);
}

var DOMsupported = 0;var standardDOMsupported = 0;var ieDOMsupported = 0;
if (document.getElementById) { standardDOMsupported = 1; DOMsupported = 1;}
else { if (document.all) {ieDOMsupported = 1; DOMsupported = 1;}
}
function findDOM(objectId) {
if (standardDOMsupported) {return (document.getElementById(objectId));}
if (ieDOMsupported) {return (document.all[objectId]);}
}

function updDsp(mtz,dst,stdz,dayz) {
var obj = findDOM('time');
obj.innerHTML = setDsp(mtz,dst,stdz,dayz);
setTimeout('updDsp('+mtz+ ','+dst+ ',"'+stdz+ '","'+dayz+ '")', 5000);
}
function setDsp(mtz,dst,stdz,dayz) {
var dayname = new Array ('Sunday', 'Monday', 'Tuesday', 'Wednesday', 'Thursday','Friday', 'Saturday', 'Sunday');
var now = new Date;
now.setUTCMinutes(now.getUTCMinutes() + (mtz + dst)*60);
var dow = now.getUTCDay();
var minute = now.getUTCMinutes();
var hour = now.getUTCHours();
if (hour > 11) {ampm = 'PM'; hour -= 12;} else {ampm = 'AM'}
if (hour == 0) {hour = 12;} if (minute < 10) {pad = ':0';} else {pad = ':';}
var txt = hour + pad + minute + ' ' + ampm + ' (';
if (dst) txt += dayz; else txt += stdz;
txt += ') on ' + dayname[dow];
return (txt);
}
                  