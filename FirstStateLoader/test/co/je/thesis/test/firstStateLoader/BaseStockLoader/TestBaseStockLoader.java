package co.je.thesis.test.firstStateLoader.BaseStockLoader;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import co.je.thesis.test.firstStateLoader.BaseStockLoader.data.TestBaseStockDataParser;
import co.je.thesis.test.firstStateLoader.BaseStockLoader.data.TestBaseStockDataRetriever;
import co.je.thesis.test.firstStateLoader.BaseStockLoader.persistence.TestBaseStockMapper;

@RunWith(Suite.class)
@SuiteClasses(value = {TestBaseStockMapper.class, TestBaseStockDataParser.class, TestBaseStockDataRetriever.class})
public class TestBaseStockLoader {

}
