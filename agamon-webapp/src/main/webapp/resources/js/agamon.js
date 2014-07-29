
////////////////////////////////////
/*
 * Commons
 */
////////////////////////////////////
function loadTab()
{
  
}

////////////////////////////////////
/* 
 * Agents
 */
////////////////////////////////////
function getMinTimestampOfAnalysys(/*map*/ slaveMap)
{
  console.log("ao");
  
  var min = null;
  
  for(var blade=0; blade < slaveMap.length; blade++)
  {
    if(min == null)
    {
      min = slaveMap[blade][0].timestamp;
    }
    else
    {
      var tmpMin = slaveMap[blade][0].timestamp;
      if(min > tmpMin)
      {
	min = tmpMin;
      }
    }
  }  
  
  return min;
};

function getSerieFromProcessingData(/*list*/ processingList, /*long*/ minTs)
{
  var cpuS = [];
  var memoryS = [];
  
  for(var i=0; i < processingList.length; i++)
  {
    var processStatus = processingList[i];
    var ts = (( processStatus.timestamp - minTs )/ 1000) / 60;
    cpuS[i] = [ ts, processStatus.cpuInUse ];
    memoryS[i] = [ ts, processStatus.memoryInUse ];
    
    console.log("ciao")
  }
  
  var result = {
    cpu: cpuS,
    mem: memoryS 
  }
  
  console.log(result);
  
  return result;
};

function plotAgents(/*Object*/ agathaProcessingData) {
	
    var minTs = getMinTimestampOfAnalysys(agathaProcessingData.slaveProcessingMap);
    
    var s1 = getSerieFromProcessingData(agathaProcessingData.slaveProcessingMap["BLADE_1"], minTs);
    
    var plotCpu = $.jqplot(
      'cpuChart',
      [s1.cpu/*, s1.mem*/],
      {
        animate: true,
        animateReplot: true,
        series:[
        /*{
            pointLabels: {
                show: true
            },
            renderer: $.jqplot.BarRenderer,
            showHighlight: false,
            yaxis: 'y2axis',
            rendererOptions: {
                // Speed up the animation a little bit.
                // This is a number of milliseconds.  
                // Default for bar series is 3000.  
                animation: {
                    speed: 2500
                },
                barWidth: 15,
                barPadding: -15,
                barMargin: 0,
                highlightMouseOver: false
            }
        },*/
        {
            rendererOptions: {
                // speed up the animation a little bit.
                // This is a number of milliseconds.
                // Default for a line series is 2500.
                animation: {
                    speed: 2000
                }
            }
        }
        ]
      }
    );
    
    var plotMem = $.jqplot(
      'memChart',
      [s1.mem],
      {
        animate: true,
        animateReplot: true,
        series:[
        /*{
            pointLabels: {
                show: true
            },
            renderer: $.jqplot.BarRenderer,
            showHighlight: false,
            yaxis: 'y2axis',
            rendererOptions: {
                // Speed up the animation a little bit.
                // This is a number of milliseconds.  
                // Default for bar series is 3000.  
                animation: {
                    speed: 2500
                },
                barWidth: 15,
                barPadding: -15,
                barMargin: 0,
                highlightMouseOver: false
            }
        },*/
        {
            rendererOptions: {
                // speed up the animation a little bit.
                // This is a number of milliseconds.
                // Default for a line series is 2500.
                animation: {
                    speed: 2000
                }
            }
        }
        ]
      }
    );
}

