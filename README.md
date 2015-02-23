#AB Allegro testing simple application

Whole application was run on windows, but some Linux systems would be probably better to run it.

For example Redis is not officially released for Windows.

##Technologies used:
-------

For main technologies I used:

* Java 8
* Spring Boot
* [Redis for windows](https://github.com/MSOpenTech/redis)
* some Guava
* jmeter for performance tests

Whole code written in IntelliJ.

##Group allocation strategy:
-------

I've implemented 2 simple groups allocation strategies.

First one is strictly keeping ratios used in groups configuration file.

Second one was actually just to play and see how randomly choosing group will be close to the ratios provided

(Sorry for stupid test for this one, hope it won't fail when you run it :P )

##REDIS
-------

I've chosen Redis as it seems to be a perfect store for this task.

I've disabled RDB saving, and enabled AOF. Because the groups won't get changed for single user, the AOF will not get rearranged.
Also it is more durable than RDB.

I of course didn't save 1_000_000 records in it, but found a note how much memory will fully saved store take:

[http://redis.io/topics/faq](http://redis.io/topics/faq)

Redis config file can be found here: \src\main\resources\persistence\redis.windows.conf

To run redis: redis-server.exe redis.windows.conf

##Performance tests
-------

I've used jmeter to run performance tests.

Configuration provided in \src\test\jmeter\ABTest.jmx can be opened in jmeter and run.

It uses 5 threads, 3000 requests each, and the beanShell Preprocessor makes each request be sent with unique ID.

You can specify max number of unique users in setUp ThreadGroup as bsh.shared.clients.

For 15000 total requests, 5 threads sending them, 3000 each this is what I received:

| Samples | Average | Median |  90% Line | Min | Max | Error % | Throughput | KB/sec |
|-------------|-------------|-------------|-------------|-------------|-------------|-------------|-------------|-------------|
| 15000  | 3  | 2  | 7  | 1  | 293 | 0.00% | 600/s | 109 |

To check the performance of not assigning new group to user, but of only reading one from redis store, we can simply rerun the jmeter test.

We then will once more send request but with those IDs that are already saved.

Here are the performance results of reading 15000 groups:

| Samples | Average | Median |  90% Line | Min | Max | Error % | Throughput | KB/sec |
|-------------|-------------|-------------|-------------|-------------|-------------|-------------|-------------|-------------|
| 15000  | 1  | 2  | 2  | 0  | 115 | 0.00% | 1000/s | 183 |

It can be easily configured to check performance of other cases also.

Jmeter test file can be found here (and easily opened in jmeter to run): \src\test\jmeter\ABTest.jmx

##Things to improve
-------

Several things could have gone better:

* should probably return to the idea of User value object instead of using String as ID - but its too late at night already.
* forgot about git no-fast-forward merging which I prefer for better readability of branches history