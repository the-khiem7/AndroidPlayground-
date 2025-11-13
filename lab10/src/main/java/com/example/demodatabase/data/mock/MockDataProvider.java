package com.example.demodatabase.data.mock;

/**
 * Global configuration for mock data
 * Toggle USE_MOCK_DATA to switch between mock and real API
 */
public class MockDataProvider {

    /**
     * Set to true to use mock data
     * Set to false to use real API/Database
     */
    public static final boolean USE_MOCK_DATA = true;

    /**
     * Simulated network delay in milliseconds
     * Set to 0 for instant responses
     */
    public static final long MOCK_DELAY_MS = 500;
}

