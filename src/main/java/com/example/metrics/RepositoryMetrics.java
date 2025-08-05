package com.example.metrics;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
public class RepositoryMetrics {
    private final MeterRegistry meterRegistry;
    private final Timer insertTimer;
    private final Timer queryTimer;
    private final Timer updateTimer;
    private final Timer deleteTimer;
    private final Counter insertCounter;
    private final Counter queryCounter;
    private final Counter errorCounter;
    
    public RepositoryMetrics(MeterRegistry meterRegistry) {
        this.meterRegistry = meterRegistry;
        
        // Initialize timers
        this.insertTimer = Timer.builder("repository.insert.duration")
            .description("Time taken for insert operations")
            .register(meterRegistry);
            
        this.queryTimer = Timer.builder("repository.query.duration")
            .description("Time taken for query operations")
            .register(meterRegistry);
            
        this.updateTimer = Timer.builder("repository.update.duration")
            .description("Time taken for update operations")
            .register(meterRegistry);
            
        this.deleteTimer = Timer.builder("repository.delete.duration")
            .description("Time taken for delete operations")
            .register(meterRegistry);
        
        // Initialize counters
        this.insertCounter = Counter.builder("repository.insert.count")
            .description("Number of insert operations")
            .register(meterRegistry);
            
        this.queryCounter = Counter.builder("repository.query.count")
            .description("Number of query operations")
            .register(meterRegistry);
            
        this.errorCounter = Counter.builder("repository.error.count")
            .description("Number of repository errors")
            .register(meterRegistry);
    }
    
    public void recordInsert(long durationMs) {
        insertTimer.record(durationMs, TimeUnit.MILLISECONDS);
        insertCounter.increment();
    }
    
    public void recordQuery(long durationMs) {
        queryTimer.record(durationMs, TimeUnit.MILLISECONDS);
        queryCounter.increment();
    }
    
    public void recordUpdate(long durationMs) {
        updateTimer.record(durationMs, TimeUnit.MILLISECONDS);
    }
    
    public void recordDelete(long durationMs) {
        deleteTimer.record(durationMs, TimeUnit.MILLISECONDS);
    }
    
    public void recordError() {
        errorCounter.increment();
    }
    
    public Timer.Sample startInsertTimer() {
        return Timer.start(meterRegistry);
    }
    
    public Timer.Sample startQueryTimer() {
        return Timer.start(meterRegistry);
    }
    
    public Timer.Sample startUpdateTimer() {
        return Timer.start(meterRegistry);
    }
    
    public Timer.Sample startDeleteTimer() {
        return Timer.start(meterRegistry);
    }
} 