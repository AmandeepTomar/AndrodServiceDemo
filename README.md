# AndrodServiceDemo
Detailed Information about Service in Android. 
Its include 
- Android Service 
- Intent Service 
- Job Intent Service
- JobService and JobScheduler
- ForeGroundService 
- WorkManage
  - Worker , CoroutineWorker 
  - WorkRequest 
    - OneTime 
    - Periodic
  - Constraints
  - Conflict Policy 
    - OneTime 
      - KEEP , REPLACE , APPEND , APPEND_OR_REPLACE 
    - Periodic 
      - KEEP , REPLACE , UPDATE(in latest)
- Some Interview Question? 
- What happen when we call Same AsyncTask instance twice at the same time?
- What Happen when started thread called start again on the same thread. 


## What is Service
A service is an android application component that can perform long running operation in the background. It does not provide UI. Once started, a service might continue running for some time, even after the user switches to another application.

A Service run in the main thread of its hosting process. by default is not creating a worker therd.

## Types of Services

### Foreground Service
A foreground service performs some operation that is noticeable to the user.Foreground services must display a Notification. Foreground services continue running even when the user isn't interacting with the app. example Music App.This notification cannot be dismissed unless the service is either stopped or removed from the foreground.
<B>WorkManager is preferable to using foreground services directly.</b>

### Background Service
A background service performs an operation that isn't directly noticed by the user. For example, if an app used a service to compact its storage, that would usually be a background service.
f your app targets API level 26 or higher, the system imposes restrictions on running background services when the app itself isn't in the foreground.
### Bound Service.
A service is bound when an application component binds to it by calling bindService(). A bound service offers a client-server interface that allows components to interact with the service, send requests, receive results, and even do so across processes with interprocess communication (IPC). A bound service runs only as long as another application component is bound to it. Multiple components can bind to the service at once, but when all of them unbind, the service is destroyed.
<b>A bound service can not be stop, untill all the component that bound to the service are unbound.
Activity , Service and Content Provider are the components that are bind to a service.</b>

#### Local Binding -> bind service in same App.
#### Remote Binding -> Two Apps comminicate using bound service.(Not in Android Documentation)
Two Apps can communicate and stablished a communication using messager and AIDL(Android Interface deffination Language). AIDL for Multithreaded environment. Messager is sutable for non-multithreaded environment.

### Bound Service Implementation using Messanger
it is a wrapper on IBinder so we will get the IBinder from messanger.
ServiceConnection is an Interface that have two method onServiceDisconnected(p0: ComponentName?)  and onServiceConnected(p0: ComponentName?, p1: IBinder?)
```
// Server side implemntation.'
// Here we crearte the instance of messanger.
private val randomMessenger = Messenger(RandomNumberRequestHandler())

// here we create the Handler
inner class RandomNumberRequestHandler : Handler(){  
override fun handleMessage(msg: Message) {  
when(msg.what){  
GET_COUNT->{  
val messageGetRandomNumber=Message.obtain(null,GET_COUNT)  
messageGetRandomNumber.arg1=getRandomNumber()  
try {  
msg.replyTo.send(messageGetRandomNumber)  
}catch (e: Exception){  
  }  }  }  
super.handleMessage(msg)  
}  }

// Client Side 

serviceConnection=object :ServiceConnection{  
override fun onServiceConnected(p0: ComponentName?, p1: IBinder?) {  
Log.e(TAG, "onServiceConnected: ${p0?.className} p1 ${p1?.isBinderAlive}")  
randomNoRequestMessenger= Messenger(p1)  
randomNoReceiveMessenger= Messenger(ReceiveRandomNumberHandler())  
isBind=true  
}  
  
override fun onServiceDisconnected(p0: ComponentName?) {  
Log.e(TAG, "onServiceDisconnected: ${p0?.className}")  
isBind=false  
randomNoRequestMessenger=null  
randomNoReceiveMessenger=null  
}  
  
override fun onBindingDied(name: ComponentName?) {  
Log.e(TAG, "onBindingDied: $name")  
super.onBindingDied(name)  
}  
  
override fun onNullBinding(name: ComponentName?) {  
Log.e(TAG, "onNullBinding: $name")  
super.onNullBinding(name)  
}  
}

inner class ReceiveRandomNumberHandler : Handler(){  
override fun handleMessage(msg: Message) {  
randomNumberValue=0;  
when(msg.what){  
GET_RANDOM_NO_FLAG->{  
randomNumberValue=msg.arg1  
binding.tvClientRandomNo.text="Random Number received from Server is $randomNumberValue"  
}  
}  
super.handleMessage(msg)  
}  
}
```


#### Lifecycle of service.
onCreate() , onStartCommand() , onDestroy()

onCreate() , onBind() , onStarrtCommand() , onUnbind() , onDestroy().

#### Service behaviour
What happen to the service when the system resuorse crunch situation occurred.
1. System will kill the service after a time , also if the service is running that have the higher priority to not killed but it will killed by system if more memory required by system.
2. System will kill the service and it will restart the service or not after some time that will depends on the cvalue return by the onStartCommand() method.
    1.	<b>START_STICKEY </b>-> Always Auto restart the service , with null intent. example  music App
    2.	<b>START_NOT_STICKEY </b>-> Not restrt the service , with intent when started Example Alarm App and data pooling in prodically.
    3.	<b>START_REDELIVER_INTENT</b> ->Auto-restrt , with latest intent. Example -> File download.

#### Intent Service
onCreate() onHandleIntent() onDestroy

Intent service is depicated , if you are using 8 or + use jobIntentService
* this one is run on worker thread so no need to start the thread
* As we notice a we go on background it stops after two minutes.

Intent service by default run on the serprate worker thread. onHandleIntent() method is thread safe so  when we call startService() multiple times it will add the all requests in queue and all the request will call in sequential manner.
Same behaviour for the bound service too.

From Android 8 or Api 26 , We have some restriction on background services, As the service goes in background, Service got stopped.

#### Job Intent service
It is helpful to run the service in background. in Android 8 +
`onStopCurrentWork()` -> true You restart the service , false means you are not care about  reschedule or restart the service  
permission ->`android.permission.BIND_JOB_SERVICE `
`onHandleWork(intent Intent){}`   // this method is called and hanlde the task here
add wake lock permission in manifest -> `to avoid stop service in background. `
to enqueue the job we need to use the
``` public static void enqueueWork(Context context , jobID, Intent work)```
as we call the enqueueWork method the onHandleWork() methd call in service.
JOB id should be unique , if we have diifernt job id without the finish the first service and enqueue another service with different job id then app got crashed.

Once the service is started it is not going to stop by using stopService(). It wil be stopSelf() when the work is done. it will stop it self.

When we call enqueue multiple times, it enqueue the job in queue and execute in the sequence as teh entered.
If we kill the app it will stop the service so it is not gurantee that it will execute , while app is killed. Buts as we retrun true in `onStopCurrentWork()` method so it will reschedule the service after some time and this will start executing the service again.

#### JobService
With the jobScheduler that will help us to execute the service perodically and with some conditions. <b>By default you run on this service is run on trhe UI thread.</b>
I want to sync a fine in evey 15 min.
if connected to internet and device is booted.
We can stop the service , by calling jobScheduler.cancle(jobID)
When we kill the app and service will stop and it will start after some time.

``` 
permission ->`android.permission.BIND_JOB_SERVICE 
			  android.permission.RECEIVE_BOOT_COMPLETED
onStartJob(JobParamters):Boolean , true -> long running , fasle -> short running
onStopJob(JobParameters):Boolean , true -> restart if killed , false -> not
onDestroy() Void
```
##### Job Scheduler

``` 
jonScheduler = getSystemService(JOB_SCHEDULER_SERVICE)
JobScheduler.JobSchedule(jonInfo)
JobInfor -> Network , battery , Wifi , JonID , backOff Policy and all. 
minimum value for preodic is 15 min. 
```
##### Foreground Service
To prevet the service got killed. perform operation that are noticable to the user.
permission -> android.permission.FOREGROUND_SERVICE.
<b>API level 29 or higher:</b> You must declare all foreground services that use location information, using the location service type.
<b>API level 30 or higher:</b> You must declare all foreground services that use the camera or microphone, using the camera or microphone service type, respectively.
<b>API level 34 or higher: </b>You must declare all foreground services with their service types.
<b>If you try to create a foreground service and its type isn't declared in the manifest, the system throws a MissingForegroundServiceTypeException upon calling startForeground().</b>

Just call teh `startForegroundService(id , notifiocation)` in `onHandleIntent() `in Service and call the service in Activity using             `ContextCompat.startForegroundService(this,foregroundService)`
