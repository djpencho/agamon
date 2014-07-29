<script>
   

  $(document).ready(function(){
    
    var agathaStiCazzi = $.ajax({
      type: "POST",
      url: "http://localhost:8080/agamon/agents/agathaProcessing.html",
      //data: "name=" + name + "&education=" + education,
      success: function(response){
      
      
      console.log("sti cazzi")
      //console.log(response);
      //console.log("eval");
      
      var r = $.parseJSON(response);
      
      plotAgents(r);
      
      // we have the response
      //$('#info').html(response);
      //$('#name').val('');
      //$('#education').val('');
      },
      error: function(e){
      alert('Error: ' + e);
      }
    });
    
    
    
   
    
    //console.log(agathaProcessingData);
    
  });
</script>
<div id="main">
CPU
</div>
<div id="cpuChart" style="height:400px; width:1800px;"></div>
<div id="main">
MEMORY
</div>
<div id="memChart" style="height:400px; width:1800px;"></div>
