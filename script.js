const AUTH_CREDS={
user:"KLH",
pass:"CSE123"
};

const DB_QUIZ='quiz_data';
const DB_SETTINGS='quiz_settings';
const DB_SUBMISSIONS='quiz_submissions';

let questions=JSON.parse(localStorage.getItem(DB_QUIZ))||[];
let settings=JSON.parse(localStorage.getItem(DB_SETTINGS))||{title:"Quiz",duration:30};
let submissions=JSON.parse(localStorage.getItem(DB_SUBMISSIONS))||[];

let currentStudent="";
let quizInterval;

function logout(){
location.reload();
}

function showTeacherLogin(){
document.getElementById('view-login').classList.add('hidden');
document.getElementById('view-teacher-login').classList.remove('hidden');
}

function showStudentLogin(){
document.getElementById('view-login').classList.add('hidden');
document.getElementById('view-student-login').classList.remove('hidden');
}

function loginTeacher(){

let u=document.getElementById('t-user').value;
let p=document.getElementById('t-pass').value;

if(u===AUTH_CREDS.user && p===AUTH_CREDS.pass){

alert("Teacher Login Successful");

}else{

document.getElementById('t-login-err').classList.remove('hidden');

}
}

function loginStudent(){

let name=document.getElementById('student-name-input').value;

if(!name){
alert("Enter your name");
return;
}

currentStudent=name;

document.getElementById('view-student-login').classList.add('hidden');
document.getElementById('view-student').classList.remove('hidden');

document.getElementById('display-student-name').innerText=name;

}

function startQuiz(){

document.getElementById('student-home').classList.add('hidden');
document.getElementById('student-attempt').classList.remove('hidden');

let duration=settings.duration*60;

quizInterval=setInterval(()=>{

duration--;

let m=Math.floor(duration/60);
let s=duration%60;

document.getElementById('timer-display').innerText=m+":"+s;

if(duration<=0){
clearInterval(quizInterval);
submitQuiz();
}

},1000);

}

function submitQuiz(e){

if(e) e.preventDefault();

clearInterval(quizInterval);

alert("Quiz Submitted");

}