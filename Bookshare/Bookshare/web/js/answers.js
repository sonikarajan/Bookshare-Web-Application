
//Function created to generate the Answer text boxes based on user input.
function createAnswerBox()
{
    var s = "";
    var param = document.getElementById("answer_dropdown").value;
    for (var i = 1; i <= param; i++)
    {
        //Create one textbox as HTML
        s += '<label>Answer ' + i + '* </label><input type="text" name="answerbox_' + i + '" required /><br><br><br>';
    }
    document.getElementById("answers_list").innerHTML = s;
}
