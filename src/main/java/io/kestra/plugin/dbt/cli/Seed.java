package io.kestra.plugin.dbt.cli;

import io.kestra.core.models.annotations.Example;
import io.kestra.core.models.annotations.Plugin;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@ToString
@EqualsAndHashCode
@Getter
@NoArgsConstructor
@Schema(
    title = "Invoke dbt `seed` command"
)
@Plugin(
    examples = {
        @Example(
            full = true,
            title = "Invoke dbt `seed` command",
            code = """
                namespace: io.kestra.tests
                id: dbt-seed
                tasks:
                  - id: working-directory
                    type: io.kestra.core.tasks.flows.WorkingDirectory
                    tasks:
                    - id: cloneRepository
                      type: io.kestra.plugin.git.Clone
                      url: https://github.com/kestra-io/dbt-demo
                      branch: main
                    - id: dbt-seed
                      type: io.kestra.plugin.dbt.cli.Seed
                      runner: DOCKER
                      dbtPath: /usr/local/bin/dbt
                      docker:
                        image: ghcr.io/kestra-io/dbt-duckdb
                      profiles: |
                        jaffle_shop:
                          outputs:
                            dev:
                              type: duckdb
                              path: ':memory:'
                              extensions:
                                - parquet
                          target: dev
                """
        )
    }
)
public class Seed extends AbstractRun {
    @Override
    protected String dbtCommand() {
        return "seed";
    }
}
