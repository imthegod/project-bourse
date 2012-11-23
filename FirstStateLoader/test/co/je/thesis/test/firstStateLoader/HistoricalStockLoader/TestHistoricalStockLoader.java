package co.je.thesis.test.firstStateLoader.HistoricalStockLoader;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import co.je.thesis.test.firstStateLoader.HistoricalStockLoader.data.TestHistoricalStockDataParser;
import co.je.thesis.test.firstStateLoader.HistoricalStockLoader.data.TestHistoricalStockDataRetriever;
import co.je.thesis.test.firstStateLoader.HistoricalStockLoader.persistence.TestHistoricalStockMapper;

@RunWith(Suite.class)
@SuiteClasses(value = {TestHistoricalStockMapper.class, TestHistoricalStockDataParser.class, TestHistoricalStockDataRetriever.class})
public class TestHistoricalStockLoader {

}
