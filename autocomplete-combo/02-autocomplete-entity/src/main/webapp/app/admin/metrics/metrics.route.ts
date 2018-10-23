import { Route } from '@angular/router';

import { AueMetricsMonitoringComponent } from './metrics.component';

export const metricsRoute: Route = {
    path: 'aue-metrics',
    component: AueMetricsMonitoringComponent,
    data: {
        pageTitle: 'metrics.title'
    }
};
