package org.javarosa.core.benchmark;

import org.javarosa.core.model.FormDef;
import org.javarosa.core.reference.ReferenceManagerTestUtils;
import org.javarosa.core.util.PathConst;
import org.javarosa.xform.parse.FormParserHelper;
import org.junit.Test;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Level;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.infra.Blackhole;
import org.openjdk.jmh.results.RunResult;
import org.openjdk.jmh.runner.NoBenchmarksException;
import org.openjdk.jmh.runner.Runner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Path;

import static org.javarosa.test.utils.ResourcePathHelper.r;

public class FormParserHelperParseESIBenchmark {
    private static final Logger logger = LoggerFactory.getLogger(FormParserHelperParseESIBenchmark.class);

    @Test
    public void
    launchBenchmark() throws Exception {
        try{
            RunResult run = new Runner(BenchmarkUtils.getJVMOptions(this.getClass().getName())).run().iterator().next();

        }catch (NoBenchmarksException nbe){

        }
    }
    @State(Scope.Thread)
    public static class FormParserHelperParseExternalSecondaryInstanceState {
        Path xFormFilePath = r("nigeria_wards_external.xml");
        Path resourcePath = xFormFilePath.getParent();
        FormDef formDef;
        @Setup(Level.Trial)
        public void
        initialize() {
            Path resourcePath = PathConst.getTestResourcePath().toPath();
            ReferenceManagerTestUtils.setUpSimpleReferenceManager("file", resourcePath);
        }
    }

    @Benchmark
    public void
    benchmark_FormParserHelper_parse_external_secondary_instance(FormParserHelperParseExternalSecondaryInstanceState state, Blackhole bh) throws IOException {
        bh.consume(FormParserHelper.parse(state.xFormFilePath));
    }


}
