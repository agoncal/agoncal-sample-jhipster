import { Component, OnInit } from '@angular/core';

import { AudConfigurationService } from './configuration.service';

@Component({
    selector: 'aud-configuration',
    templateUrl: './configuration.component.html'
})
export class AudConfigurationComponent implements OnInit {
    allConfiguration: any = null;
    configuration: any = null;
    configKeys: any[];
    filter: string;
    orderProp: string;
    reverse: boolean;

    constructor(private configurationService: AudConfigurationService) {
        this.configKeys = [];
        this.filter = '';
        this.orderProp = 'prefix';
        this.reverse = false;
    }

    keys(dict): Array<string> {
        return dict === undefined ? [] : Object.keys(dict);
    }

    ngOnInit() {
        this.configurationService.get().subscribe(configuration => {
            this.configuration = configuration;

            for (const config of configuration) {
                if (config.properties !== undefined) {
                    this.configKeys.push(Object.keys(config.properties));
                }
            }
        });

        this.configurationService.getEnv().subscribe(configuration => {
            this.allConfiguration = configuration;
        });
    }
}
